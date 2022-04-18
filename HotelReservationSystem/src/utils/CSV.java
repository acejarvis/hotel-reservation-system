package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CSV {
    private String path;
    private String delimiter;
    private DataFrame df;
    public String[] columns;

    public CSV(String path) {
        this(path, ",");
    }

    public CSV(String path, String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
        this.df = new DataFrame(this.path, delimiter);
        this.columns = df.columns;
    }

    public Boolean insert(String[] value) {
        int id = autoIncrement();
        if (df.countCols() == value.length + 1) {
            df.values.put(id, (id + delimiter + df.buildRow(value)).split(delimiter));
            return df.buildCSV(this.path);
        }
        return false;

    }

    public Boolean update(int id, String column, String value) {
        String[] row = search(this.columns[0], id).get(0);
        for (int i = 0; i < this.columns.length; i++) {
            if (this.columns[i].equals(column)) {
                row[i] = value;
            }
        }
        return update(id, Arrays.copyOfRange(row, 1, row.length));
    }

    public Boolean update(int id, String[] value) {
        if (df.countCols() == value.length + 1) {
            df.values.put(id, (id + delimiter + df.buildRow(value)).split(delimiter));
            return df.buildCSV(this.path);
        }
        return false;
    }

    public Boolean delete(int id) {
        if (df.values.remove(id) == null)
            return false;
        return df.buildCSV(this.path);

    }

    public Map<Integer, String[]> search(String column, String value) {

        Map<Integer, String[]> resultados = new HashMap<>();

        for (String[] row : df.values.values())
            if (row[df.columnsMap.get(column)].matches(value)) {
                resultados.put(resultados.size(), row);
            }

        return resultados;
    }

    public Map<Integer, String[]> search(String column, int start, int end) {

        Map<Integer, String[]> resultados = new HashMap<>();

        for (String[] row : df.values.values())
            if (Integer.parseInt(row[df.columnsMap.get(column)]) >= start
                    && Integer.parseInt(row[df.columnsMap.get(column)]) <= end) {
                resultados.put(resultados.size(), row);
            }

        return resultados;
    }

    public Map<Integer, String[]> search(String column, int value) {
        return search(column, Integer.toString(value));
    }

    public Map<Integer, String[]> getAll() {
        return df.values;
    }

    private int autoIncrement() {
        int id = -1;
        for (int key : df.values.keySet())
            if (key > id)
                id = key;
        return id + 1;
    }
}

class DataFrame {

    public Map<String, Integer> columnsMap;
    public Map<Integer, String[]> values;
    public String[] columns;
    private String delimiter;

    public DataFrame(String path, String delimiter) {

        this.delimiter = delimiter;

        File file = new File(path);
        values = new HashMap<>();
        columnsMap = new HashMap<>();

        try (Scanner inputStream = new Scanner(file)) {
            String data = inputStream.nextLine();
            String[] dados = data.split(this.delimiter);
            columns = dados;
            for (int i = 0; i < dados.length; i++) {
                columnsMap.put(dados[i], i);
            }

            String[] row;
            while (inputStream.hasNext()) {
                data = inputStream.nextLine();
                row = data.split(delimiter);
                values.put(Integer.parseInt(row[0]), row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String buildRow(String[] arr) {
        StringBuilder sb = new StringBuilder();

        for (Object value : arr)
            sb.append(value + this.delimiter);
        String r = sb.toString();
        return r.substring(0, r.length() - 1) + "\n";
    }

    public boolean buildCSV(String path) {

        StringBuilder sb = new StringBuilder();
        sb.append(buildRow(columns));
        for (String[] row : values.values())
            sb.append(buildRow(row));

        String r = sb.toString().replace("\n\n", "\n");

        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(path));
            output.append(r);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countRows() {
        return values.size();
    }

    public int countCols() {
        return columnsMap.values().size();
    }
}