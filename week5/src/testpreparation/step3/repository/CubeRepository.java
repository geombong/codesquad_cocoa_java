package testpreparation.step3.repository;

import testpreparation.step3.domain.Cube;

import java.util.*;

public class CubeRepository {
    private static final CubeRepository instance = new CubeRepository();
    private static final Map<Integer, Cube> cubeList = new HashMap<>();
    private static int sequence = 0;

    public void save(Cube cube) {
        cube.setId(sequence);
        cubeList.put(sequence, cube);
        sequence++;
    }

    public static CubeRepository getInstance() {
        return instance;
    }

    public void printCube() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        topAndBottom(sb, count);
        for (int c = 0; c < 3; c++) {
            center(sb, c);
        }
        count = 5;
        topAndBottom(sb, count);
        System.out.println(sb);
    }

    private void center(StringBuilder sb, int c) {
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(cubeList.get(i).getCube()[c][j]).append(" ");
            }
            sb.append("    ");
        }
        sb.append("\n");
    }

    private void topAndBottom(StringBuilder sb, int count) {
        sb.append("\n");
        for (int i = 0; i < 3; i++) {
            sb.append("          ");
            for (int j = 0; j < 3; j++) {
                sb.append(cubeList.get(count).getCube()[i][j]).append(" ");
            }
            sb.append("\n");
        }
        sb.append("\n");
    }

    public void leftRotate(int cubeKey, int cubeLineIndex) {
        List<String[]> value = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            value.add(cubeList.get((i % 4) + 1).getCube()[cubeLineIndex]);
        }
        for (int j = 0; j < value.size(); j++) {
            cubeList.get(j+1).getCube()[cubeLineIndex] = value.get(j);
        }
        reverseRotate(cubeKey);
    }

    public void rightRotate(int cubeKey, int cubeLineIndex) {
        List<String[]> value = new ArrayList<>();
        value.add(cubeList.get(4).getCube()[cubeLineIndex]);
        for (int i = 0; i < 3; i++) {
            value.add(cubeList.get(i + 1 % 4).getCube()[cubeLineIndex]);
        }
        for (int j = 0; j < value.size(); j++) {
            cubeList.get(j+1).getCube()[cubeLineIndex] = value.get(j);
        }
        rotate(cubeKey);
    }

    public void upRotate(int colIndex) {
        int valueIndex = 0;
        List<String> value = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i == 1 || i == 3) { continue; }
            for (int j = 0; j < 3; j++) {
                value.add(cubeList.get(i).getCube()[j][colIndex]);
            }
        }
        for (int i = 0; i < 6; i++) {
            if (i == 1 || i == 3) { continue; }
            for (int j = 0; j < 3; j++) {
                cubeList.get(i).getCube()[j][colIndex] = value.get((valueIndex + 3) % value.size());
                valueIndex++;
            }
        }
    }

    public void rotate(int cubeKey) {
        String[][] tempArr = new String[3][3];
        for (int i = 0; i < tempArr.length; i++) {
            for (int j = 0; j < tempArr[i].length; j++) {
                tempArr[j][2 - i] = cubeList.get(cubeKey).getCube()[i][j];
            }
        }
        for (int i = 0; i < tempArr.length; i++) {
            for (int j = 0; j < tempArr[i].length; j++) {
                cubeList.get(cubeKey).getCube()[i][j] = tempArr[i][j];
            }
        }
    }

    public void reverseRotate(int cubeKey) {
        for (int i = 0; i < 2; i++) {
            rotate(cubeKey);
        }
    }
}