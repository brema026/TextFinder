package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apache.poi.ss.formula.functions.T;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @FXML
    public ListView<String> resultList;
    private FinderController finderController;
    private TextAreaController textAreaController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Agregar un manejador de eventos de doble clic al ListView
        resultList.setOnMouseClicked(this::handleItemClick);
    }

    private void handleItemClick(javafx.scene.input.MouseEvent event) {
        if (event.getClickCount() == 1) {
            String selectedItem = resultList.getSelectionModel().getSelectedItem();
            System.out.println("SelectedItem: " + selectedItem); // Debugging

            // Dividir la cadena por el separador " --> "
            String[] parts = selectedItem.split(" --> ");

            if (parts.length == 3) {
                String searchText = parts[0].trim();
                String selectedFileName = parts[1].trim();
                String positionPart = parts[2].trim();

                try {
                    int[] position = getPositionFromSelectedItem(positionPart);
                    if (finderController != null) {
                        finderController.updateTextArea(selectedFileName);
                        System.out.println("Listo");

                        // Llamar a focusWord solo con el primer valor del índice
                        textAreaController.focusWord(position[0]);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Formato de selectedItem inesperado: " + selectedItem);
            }
        }
    }

    private int[] getPositionFromSelectedItem(String positionPart) {
        try {
            // Deserializa la cadena a un array de enteros
            String[] parts = positionPart.split(",");
            int[] position = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                position[i] = Integer.parseInt(parts[i].trim());
            }
            return position;
        } catch (NumberFormatException e) {
            // Manejar el caso en el que la posición no sea un número válido
            throw new IllegalArgumentException("No se pudo extraer la posición del archivo: " + positionPart, e);
        }
    }

    public void setFinderController(FinderController finderController) {
        this.finderController = finderController;
    }

    public void displayStringResults(String[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        List<String> allFileNames = finderController.getAllFileNames(); // Obtener todos los nombres de archivo

        for (String result : results) {
            // Crear el elemento que mostrará el texto buscado junto con el nombre del archivo en el que se encontró
            for (String fileName : allFileNames) {
                if (result.contains(fileName)) {
                    String item = result;
                    items.add(item);
                }
            }
        }

        resultList.setItems(items);
    }


    public void displayResults(Result[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        for (Result result : results) {
            // Obtener el nombre del archivo de la instancia de Result
            String fileName = result.getDocument().getFileName();
            String positionString = serializePosition(result.getPosition());
            System.out.println("Nombre del archivo: " + fileName);
            // Obtener la frase encontrada en el documento
            String item = searchText + "  -->  " + fileName + " --> " + positionString;
            items.add(item);
        }

        resultList.setItems(items);
    }
    private String serializePosition(int[] position) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < position.length; i++) {
            sb.append(position[i]);
            if (i < position.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public void setTextAreaController(TextAreaController textAreaController) {
        this.textAreaController = textAreaController;}

    public void quicksort(ObservableList<String> results) {
        quicksort(results, 0, results.size() - 1);
    }

    private void quicksort(ObservableList<String> results, int low, int high) {
        if (low < high) {
            int pi = partition(results, low, high);
            quicksort(results, low, pi - 1);
            quicksort(results, pi + 1, high);
        }
    }

    private int partition(ObservableList<String> results, int low, int high) {
        String pivot = results.get(high);
        String pivotName = pivot.split(" --> ")[1].toLowerCase();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            String currentName = results.get(j).split(" --> ")[1].toLowerCase();
            if (currentName.compareTo(pivotName) <= 0) {
                i++;
                swap(results, i, j);
            }
        }
        swap(results, i + 1, high);
        return i + 1;
    }

    private void swap(ObservableList<String> results, int i, int j) {
        if (i == j) return;
        String temp = results.get(i);
        results.set(i, results.get(j));
        results.set(j, temp);
    }

    public void bubblesort(ObservableList<String> results) {
        int n = results.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                String date1 = results.get(j).split(" --> ")[2];
                String date2 = results.get(j + 1).split(" --> ")[2];
                if (date1.compareTo(date2) > 0) {
                    swap(results, j, j + 1);
                }
            }
        }
    }

    public void radixsort(ObservableList<String> results) {
        int max = getMaxSize(results);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSortBySize(results, exp);
        }
    }

    private int getMaxSize(ObservableList<String> results) {
        int max = results.stream()
                .mapToInt(result -> Integer.parseInt(result.split(" --> ")[2].replaceAll("[^0-9]", "")))
                .max()
                .orElse(0);
        return max;
    }

    private void countSortBySize(ObservableList<String> results, int exp) {
        int n = results.size();
        String[] output = new String[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            int size = Integer.parseInt(results.get(i).split(" --> ")[2].replaceAll("[^0-9]", ""));
            count[(size / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int size = Integer.parseInt(results.get(i).split(" --> ")[2].replaceAll("[^0-9]", ""));
            output[count[(size / exp) % 10] - 1] = results.get(i);
            count[(size / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            results.set(i, output[i]);
        }
    }
}
