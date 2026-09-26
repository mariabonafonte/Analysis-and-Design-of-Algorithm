import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Marker;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.plotly.components.Line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class AnalysisResult {

        public Table dataframe;
        public double[] coefPattern;
        public double[] patternSizes;

        public AnalysisResult(Table dataframe, double[] coefPattern, double[] patternSizes) {
            this.dataframe = dataframe;
            this.coefPattern = coefPattern;
            this.patternSizes = patternSizes;
        }
    

    public static void createCharts()  throws Exception{
        // 1. Cargar y procesar datos
        AnalysisResult dataQuick = readStats("QuickSearch");
        AnalysisResult dataBrute = readStats("NaiveStringSearch");

        // 2. Gráficos de dispersión con línea de ajuste
        showGraphicalRepresentation(dataBrute.dataframe, "NaiveStringSearch");
        showGraphicalRepresentation(dataQuick.dataframe, "QuickSearch");

        // 3. Crear DataFrame de comparación
        Table patternSizeVariance = Table.create("PatternSizeVariance")
                .addColumns(
                        DoubleColumn.create("quick_pattern_size", dataQuick.patternSizes),
                        DoubleColumn.create("quick_coefficient", dataQuick.coefPattern),
                        DoubleColumn.create("brute_pattern_size", dataBrute.patternSizes),
                        DoubleColumn.create("brute_coefficient", dataBrute.coefPattern)
                );

        // Ordenar la tabla por el tamaño del patrón (de menor a mayor)
        patternSizeVariance = patternSizeVariance.sortOn("quick_pattern_size");

        // Mostrar gráfico de comparación (constante de proporcionalidad)
        showComparisonGraph(patternSizeVariance);

        // 4. Equivalente a kable(): Imprimir resumen de datos
        Table summaryTable = patternSizeVariance.selectColumns("quick_pattern_size", "brute_coefficient", "quick_coefficient");
        summaryTable.column(0).setName("pattern length");
        summaryTable.column(1).setName("naive method");
        summaryTable.column(2).setName("QuickSearch");

        System.out.println("\n--- Table: Proportionality constant in the O(n) model ---");
        System.out.println(summaryTable.print());
    }

    /**
     * Equivalente a la función read_stats() en R
     */
    public static AnalysisResult readStats(String name) throws Exception {
        String filename = name + "-stats.txt";
        
        List<Double> textSizesList = new ArrayList<>();
        List<Double> patternSizesList = new ArrayList<>();
        List<double[]> observationsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                
                double textSize = Double.parseDouble(parts[0]);
                double patternSize = Double.parseDouble(parts[1]);
                
                double[] obs = new double[parts.length - 2];
                for (int i = 2; i < parts.length; i++) {
                    obs[i - 2] = Double.parseDouble(parts[i]);
                }

                textSizesList.add(textSize);
                patternSizesList.add(patternSize);
                observationsList.add(obs);
            }
        }

        int nRows = textSizesList.size();
        double[] textSizes = textSizesList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] patternSizes = patternSizesList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] compMean = new double[nRows];
        double[] compSe = new double[nRows];

        // Calcular Media y Error Estándar (sd / sqrt(n))
        for (int i = 0; i < nRows; i++) {
            DescriptiveStatistics stats = new DescriptiveStatistics(observationsList.get(i));
            compMean[i] = stats.getMean();
            compSe[i] = stats.getStandardDeviation() / Math.sqrt(stats.getN());
        }

        // Crear DataFrame base en Tablesaw
        Table dataframe = Table.create(name)
                .addColumns(
                        DoubleColumn.create("text_size", textSizes),
                        DoubleColumn.create("pattern_size", patternSizes),
                        DoubleColumn.create("compmean", compMean),
                        DoubleColumn.create("compse", compSe)
                );

        // Obtener valores únicos de tamaños de patrón
        DoubleColumn uniquePatternsCol = dataframe.doubleColumn("pattern_size").unique();
        double[] uniquePatterns = uniquePatternsCol.asDoubleArray();
        double[] coefPattern = new double[uniquePatterns.length];

        DoubleColumn predictedPattern = DoubleColumn.create("predicted_pattern", new double[nRows]);

        // Ajuste lineal sin intercepto: lm(compmean ~ 0 + text_size)
        for (int i = 0; i < uniquePatterns.length; i++) {
            double pSize = uniquePatterns[i];
            Table subdata = dataframe.where(dataframe.doubleColumn("pattern_size").isEqualTo(pSize));

            SimpleRegression regression = new SimpleRegression(false); // false = sin intercepto
            DoubleColumn subText = subdata.doubleColumn("text_size");
            DoubleColumn subCompMean = subdata.doubleColumn("compmean");

            for (int j = 0; j < subdata.rowCount(); j++) {
                regression.addData(subText.get(j), subCompMean.get(j));
            }

            double slope = regression.getSlope();
            coefPattern[i] = slope;

            // Predecir compmean = text_size * pendiente
            for (int j = 0; j < nRows; j++) {
                if (patternSizes[j] == pSize) {
                    predictedPattern.set(j, textSizes[j] * slope);
                }
            }
        }

        dataframe.addColumns(predictedPattern);

        return new AnalysisResult(dataframe, coefPattern, uniquePatterns);
    }

    /**
     * Equivalente a la función graphical_representation() usando Plotly en Tablesaw
     */       
    public static void showGraphicalRepresentation(Table df, String name) {
        DoubleColumn x = df.doubleColumn("text_size");
        DoubleColumn y = df.doubleColumn("compmean");

        String[] colors = new String[] {
                "#3182bd", "#6baed6", "#9ecae1", "#c6dbef", "#e6550d",
                "#fdae6b", "#fdd0a2", "#31a354", "#a1d99b", "#c7e9c0"
            };
        
        // 1. Puntos de datos (geom_point)
        ScatterTrace dataPoints = ScatterTrace.builder(x, y)
                .mode(ScatterTrace.Mode.MARKERS)
                .name("mean")
                .marker(Marker.builder().color("blue").size(8).build())
                .build();

        List<ScatterTrace> traces = new ArrayList<>();
        traces.add(dataPoints);

        // 2. Crear una línea ajustada por cada tamaño de patrón único
        DoubleColumn uniquePatterns = df.doubleColumn("pattern_size").unique();
        boolean firstLine = true; // Para mostrar solo una entrada en la leyenda
        uniquePatterns.sortAscending();
        int count = 0;
        for (double pSize : uniquePatterns) {
        	
            Table subdata = df.where(df.doubleColumn("pattern_size").isEqualTo(pSize));
            
            // Aseguramos que los puntos de la línea estén ordenados por x
            subdata = subdata.sortOn("text_size");

            ScatterTrace.ScatterBuilder lineBuilder = ScatterTrace.builder(
                    subdata.doubleColumn("text_size"), 
                    subdata.doubleColumn("predicted_pattern")
            )
            .mode(ScatterTrace.Mode.LINE)
            .line(Line.builder().color(colors[count]).dash(Line.Dash.DOT).build());

            count = (count+1)%colors.length;
            if (firstLine) {
                lineBuilder.name("linear fit - pattern length: " + pSize);
                //firstLine = false;
            } else {
                lineBuilder.showLegend(false); // Oculta duplicados en la leyenda
            }

            traces.add(lineBuilder.build());
        }

        Layout layout = Layout.builder()
                .title(name + " for different pattern lengths")
                .xAxis(Axis.builder().title("text length").build())
                .yAxis(Axis.builder().title("comparisons").build())
                .build();

        // 3. Crear la figura pasándole la lista de trazadas convertida a array
        Figure figure = new Figure(layout, traces.toArray(new ScatterTrace[0]));

        String divId = "chart_div";
        String fullHtml = "<!DOCTYPE html>\n<html>\n<head>\n"
                + "  <script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>\n"
                + "</head>\n<body>\n"
                + "  <div id=\"" + divId + "\"></div>\n"
                + figure.asJavascript(divId) + "\n"
                + "</body>\n</html>";

        File outputFile = new File(name + "_chart.html");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(fullHtml);
            System.out.println("Gráfico guardado en: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Equivalente al gráfico comparativo con escala logarítmica
     */
    public static void showComparisonGraph(Table patternSizeVariance) {
        DoubleColumn xQuick = patternSizeVariance.doubleColumn("quick_pattern_size");
        DoubleColumn yQuick = patternSizeVariance.doubleColumn("quick_coefficient");
        DoubleColumn xBrute = patternSizeVariance.doubleColumn("brute_pattern_size");
        DoubleColumn yBrute = patternSizeVariance.doubleColumn("brute_coefficient");

        // Trazado QuickSearch
        ScatterTrace quickTrace = ScatterTrace.builder(xQuick, yQuick)
                .mode(ScatterTrace.Mode.LINE_AND_MARKERS)
                .name("QuickSearch")
                .marker(Marker.builder().color("blue").build())
                .build();

        // Trazado BruteForce
        ScatterTrace bruteTrace = ScatterTrace.builder(xBrute, yBrute)
                .mode(ScatterTrace.Mode.LINE_AND_MARKERS)
                .name("BruteForce")
                .marker(Marker.builder().color("black").build())
                .build();

        // Escala Logarítmica en el Eje Y
        Layout layout = Layout.builder()
                .title("QuickSearch vs BruteForce: influence of the pattern length")
                .xAxis(Axis.builder().title("pattern length").build())
                .yAxis(Axis.builder().title("proportionality constant").type(Axis.Type.LOG).build())
                .build();

        Figure figure = new Figure(layout, quickTrace, bruteTrace);

        // ID usando guion bajo
        String divId = "comparison_div";

        // Generamos el HTML completo incluyendo el div contenedor
        String fullHtml = "<!DOCTYPE html>\n<html>\n<head>\n"
                + "  <script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>\n"
                + "</head>\n<body>\n"
                + "  <div id=\"" + divId + "\"></div>\n"
                + figure.asJavascript(divId) + "\n"
                + "</body>\n</html>";

        File outputFile = new File("comparison_chart.html");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(fullHtml);
            System.out.println("Gráfico comparativo guardado en: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}