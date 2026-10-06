import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.SimpleCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.components.Marker;
import tech.tablesaw.plotly.components.Line;
import tech.tablesaw.plotly.components.Symbol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiophantineAnalysis {

	public static void createCharts()  throws Exception{
		
        String dataPath = "Mediant-Approximation-stats.txt";

        List<Double> precisionList = new ArrayList<>();
        List<Double> timeQ2List = new ArrayList<>();
        List<Double> timeQ1List = new ArrayList<>();
        List<Double> timeQ3List = new ArrayList<>();
        List<Double> expList = new ArrayList<>();

        // 1. Lectura del archivo y cálculo de estadísticas descriptivas por fila
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split("\\s+");
                double kExp = Double.parseDouble(tokens[0]);
                double precision = 0.5 * Math.pow(10, -kExp);

                DescriptiveStatistics stats = new DescriptiveStatistics();
                for (int i = 1; i < tokens.length; i++) {
                    stats.addValue(Double.parseDouble(tokens[i]));
                }

                expList.add(kExp);
                precisionList.add(precision);
                timeQ1List.add(stats.getPercentile(25)); // Q1 (low)
                timeQ2List.add(stats.getPercentile(50)); // Q2 (mediana/time)
                timeQ3List.add(stats.getPercentile(75)); // Q3 (upp)
            }
        }

        // 2. Construcción de la tabla resumen en Tablesaw
        Table summaryTable = Table.create("Summary")
                .addColumns(
                        DoubleColumn.create("precision", precisionList),
                        DoubleColumn.create("time", timeQ2List),
                        DoubleColumn.create("low", timeQ1List),
                        DoubleColumn.create("upp", timeQ3List)
                );

        System.out.println("--- Resumen de Datos ---");
        System.out.println(summaryTable.printAll());

        // 3. Ajustes de modelos con Apache Commons Math 3
        int n = precisionList.size();
        WeightedObservedPoints points = new WeightedObservedPoints();
        for (int i = 0; i < n; i++) {
            points.add(precisionList.get(i), timeQ2List.get(i));
        }

        // --- Modelo 1: Ley de Potencias -> time = a * (1 / precision)^b ---
        ParametricUnivariateFunction powerLawFunc = new ParametricUnivariateFunction() {
            @Override
            public double value(double x, double... parameters) {
                double a = parameters[0];
                double b = parameters[1];
                return a * Math.pow(1.0 / x, b);
            }

            @Override
            public double[] gradient(double x, double... parameters) {
                double a = parameters[0];
                double b = parameters[1];
                double invX = 1.0 / x;
                double invXToB = Math.pow(invX, b);
                return new double[] {
                        invXToB,                       // d/da
                        a * invXToB * Math.log(invX)   // d/db
                };
            }
        };

        // Estimación de valores iniciales (a0, b0) como en R
        double timeN = timeQ2List.get(n - 1);
        double timeN1 = timeQ2List.get(n - 2);
        double precN = precisionList.get(n - 1);
        double precN1 = precisionList.get(n - 2);

        double b0 = -Math.log(timeN / timeN1) / Math.log(precN / precN1);
        double a0Pwr = timeN / Math.pow(1.0 / precN, b0);

        SimpleCurveFitter pwrFitter = SimpleCurveFitter.create(powerLawFunc, new double[]{a0Pwr, b0});
        double[] pwrParams = pwrFitter.fit(points.toList());
        double aPwr = pwrParams[0];
        double bPwr = pwrParams[1];

        System.out.printf("%nPower Law Fit: a = %.3e, b = %.4f%n", aPwr, bPwr);

        // --- Modelo 2: Ajuste Logarítmico -> time = a * log(1 / precision) ---
        ParametricUnivariateFunction logFunc = new ParametricUnivariateFunction() {
            @Override
            public double value(double x, double... parameters) {
                double a = parameters[0];
                return a * Math.log(1.0 / x);
            }

            @Override
            public double[] gradient(double x, double... parameters) {
                return new double[] { Math.log(1.0 / x) }; // d/da
            }
        };

        double a0Log = timeN / Math.log(1.0 / precN);
        SimpleCurveFitter logFitter = SimpleCurveFitter.create(logFunc, new double[]{a0Log});
        double[] logParams = logFitter.fit(points.toList());
        double aLog = logParams[0];

        System.out.printf("Logarithmic Fit: a = %.3e%n", aLog);

        // 4. Generación de datos predichos en malla fina
        double minExp = expList.get(0);
        double maxExp = expList.get(n - 1);
        List<Double> xxPrecision = new ArrayList<>();
        List<Double> yyPwr = new ArrayList<>();
        List<Double> yyLog = new ArrayList<>();

        for (double exp = minExp; exp <= maxExp; exp += 0.01) {
            double xVal = 0.5 * Math.pow(10, -exp);
            xxPrecision.add(xVal);
            yyPwr.add(powerLawFunc.value(xVal, pwrParams));
            yyLog.add(logFunc.value(xVal, logParams));
        }

        // 5. Creación del gráfico con Tablesaw (Plotly)
        
        // Traza de Datos Expermentales (puntos + barras de error cuartílicas)
        double[] arrayErrorY = new double[n];
        double[] arrayErrorMinusY = new double[n];
        for (int i = 0; i < n; i++) {
            arrayErrorY[i] = timeQ3List.get(i) - timeQ2List.get(i);
            arrayErrorMinusY[i] = timeQ2List.get(i) - timeQ1List.get(i);
        }

        ScatterTrace dataTrace = ScatterTrace.builder(
                summaryTable.doubleColumn("precision"),
                summaryTable.doubleColumn("time"))
                .name("data")
                .mode(ScatterTrace.Mode.MARKERS)
                .marker(Marker.builder().size(8).color("blue").symbol(Symbol.CIRCLE).build())
                .build();

        // Traza del ajuste de Ley de Potencias
        ScatterTrace pwrTrace = ScatterTrace.builder(
                DoubleColumn.create("pwrX", xxPrecision),
                DoubleColumn.create("pwrY", yyPwr))
                .name(String.format("Power law fit: %.3e (1/ε)^%.4f", aPwr, bPwr))
                .mode(ScatterTrace.Mode.LINE)
                .line(Line.builder().color("red").width(2).build())
                .build();

        // Traza del ajuste Logarítmico
        ScatterTrace logTrace = ScatterTrace.builder(
                DoubleColumn.create("logX", xxPrecision),
                DoubleColumn.create("logY", yyLog))
                .name(String.format("Logarithmic fit: %.3e log(1/ε)", aLog))
                .mode(ScatterTrace.Mode.LINE)
                .line(Line.builder().color("black").width(2).build())
                .build();

        Layout layout = Layout.builder()
                .title("Rational Approximation Time vs. Precision")
                .xAxis(Axis.builder().title("precision (ε)").type(Axis.Type.LOG).build())
                .yAxis(Axis.builder().title("time (s)").build())
                .showLegend(true)
                .build();

        File outputFile = new File("diophantine_analysis.html");
        Figure figure = new Figure(layout, pwrTrace, logTrace, dataTrace);
        Plot.show(figure,outputFile);
        
        String content = Files.readString(outputFile.toPath(), StandardCharsets.UTF_8);
        String updatedContent = content.replaceAll(
        	    "https://cdn.plot.ly/plotly-latest.min.js", 
        	    "plotly-latest.min.js"
        	);
        Files.writeString(outputFile.toPath(), updatedContent, StandardCharsets.UTF_8);
        
        
    }
}