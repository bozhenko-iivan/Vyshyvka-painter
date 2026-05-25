package ua.university;

import java.util.HashMap;
import java.util.Map;

public class VyshyvkaRenderer {
    private final Map<String, int[][]> alphabet = new HashMap<>();

    static class Box {
        int x;
        int y;
        int width;
        int height;
    }

    public VyshyvkaRenderer() {
        initAlphabet();
    }

    private void initAlphabet() {
        alphabet.put("0", new int[][]{ {1, 1, 1, 0, 0, 0}, {1, 0, 1, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 1, 1}, {0, 0, 0, 1, 0, 1}, {0, 0, 0, 1, 1, 1} });
        alphabet.put("1", new int[][]{ {0, 1, 0}, {0, 1, 0}, {1, 1, 1} });
        alphabet.put("2", new int[][]{ {1, 1, 0}, {0, 1, 0}, {1, 1, 1} });
        alphabet.put("3", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 1, 1, 1, 1} });
        alphabet.put("4", new int[][]{ {0, 1, 0, 1, 0}, {1, 1, 1, 1, 1}, {0, 1, 0, 1, 0}, {1, 1, 1, 1, 1}, {0, 1, 0, 1, 0} });
        alphabet.put("5", new int[][]{ {1, 0, 1, 1, 1}, {1, 0, 1, 0, 1}, {1, 1, 1, 0, 1} });
        alphabet.put("6", new int[][]{ {0, 1, 1, 1, 0}, {1, 1, 0, 1, 1}, {0, 1, 1, 1, 0} });
        alphabet.put("7", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {0, 1, 0, 1, 0}, {1, 1, 1, 1, 1} });
        alphabet.put("8", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 1, 0, 1, 1}, {0, 1, 1, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("9", new int[][]{ {0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 1, 0, 0}, {1, 1, 1, 0, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1} });
        alphabet.put("10", new int[][]{ {0, 0, 2, 2}, {2, 2, 0, 0} });
        alphabet.put("100", new int[][]{ {0, 2, 0}, {2, 2, 2} });
        alphabet.put("1000", new int[][]{ {2, 2, 2, 0, 2, 0}, {0, 2, 0, 2, 2, 2} });
        alphabet.put(" ", new int[][]{ {0, 0, 1}, {1, 1, 1}, {1, 0, 0} });
        alphabet.put(" >", new int[][]{ {0, 0, 1}, {0, 1, 0}, {1, 0, 0} });
        alphabet.put("а", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 0, 0, 0, 1} });
        alphabet.put("а>", new int[][]{ {1, 1, 1}, {0, 1, 1}, {0, 0, 1} });
        alphabet.put("б", new int[][]{ {0, 1, 1, 0, 0, 0, 1, 1, 0}, {1, 0, 0, 1, 0, 1, 0, 0, 1}, {0, 1, 0, 0, 1, 0, 0, 1, 0}, {0, 0, 0, 0, 1, 0, 0, 0, 0} });
        alphabet.put("б>", new int[][]{ {1, 1, 1, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 1, 1, 1}, {0, 1, 0, 0, 1}, {1, 0, 0, 1, 1} });
        alphabet.put("в", new int[][]{ {0, 0, 1, 0, 0}, {1, 0, 1, 0, 1}, {1, 0, 1, 0, 1}, {0, 0, 1, 0, 0} });
        alphabet.put("в>", new int[][]{ {0, 1, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 1, 0, 0, 1}, {0, 0, 0, 1, 0} });
        alphabet.put("г", new int[][]{ {0, 1, 0}, {1, 1, 1}, {1, 1, 1} });
        alphabet.put("г>", new int[][]{ {0, 1, 0, 1}, {1, 1, 1, 0}, {0, 1, 1, 1}, {0, 0, 1, 0} });
        alphabet.put("ѓ", new int[][]{ {0, 1, 0}, {1, 1, 1}, {1, 1, 1}, {0, 1, 0} });
        alphabet.put("ѓ>", new int[][]{ {0, 1, 0, 1}, {1, 1, 1, 0}, {0, 1, 1, 1}, {0, 0, 1, 0}, {1, 0, 0, 0} });
        alphabet.put("д", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 1, 0, 1}, {0, 1, 1, 1, 0} });
        alphabet.put("д>", new int[][]{ {0, 0, 0, 1, 0}, {1, 0, 1, 0, 1}, {1, 0, 1, 1, 0}, {0, 1, 0, 0, 0} });
        alphabet.put("е", new int[][]{ {0, 0, 1, 0, 0}, {1, 0, 1, 0, 1}, {1, 1, 1, 1, 1} });
        alphabet.put("е>", new int[][]{ {0, 1, 0, 1}, {1, 0, 1, 0}, {0, 1, 0, 1}, {0, 0, 1, 0} });
        alphabet.put("є", new int[][]{ {1, 0, 1, 0, 1}, {1, 0, 1, 0, 1}, {0, 1, 1, 1, 0} });
        alphabet.put("є>", new int[][]{ {1, 0, 0, 0}, {1, 0, 1, 0}, {0, 1, 0, 0}, {0, 0, 1, 1} });
        alphabet.put("ж", new int[][]{ {1, 0, 1, 0, 1}, {0, 1, 1, 1, 0}, {1, 0, 1, 0, 1} });
        alphabet.put("ж>", new int[][]{ {0, 1, 0, 0, 0}, {1, 1, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 1, 0, 1, 1}, {0, 0, 0, 1, 0} });
        alphabet.put("з", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 1, 0, 1}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("з>", new int[][]{ {1, 0, 1, 0, 1}, {0, 1, 0, 1, 0}, {1, 0, 1, 0, 1}, {0, 1, 0, 1, 0}, {1, 0, 1, 0, 1} });
        alphabet.put("и", new int[][]{ {0, 0, 1, 0, 0}, {1, 0, 1, 0, 1}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("и>", new int[][]{ {0, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 1, 0, 0, 1}, {1, 0, 1, 1, 0} });
        alphabet.put("і", new int[][]{ {1}, {0}, {1}, {1} });
        alphabet.put("і>", new int[][]{ {0, 0, 0, 1}, {0, 0, 0, 0}, {0, 1, 0, 0}, {1, 0, 0, 0} });
        alphabet.put("ї", new int[][]{ {1, 0, 1}, {0, 0, 0}, {0, 1, 0}, {0, 1, 0} });
        alphabet.put("ї>", new int[][]{ {0, 1, 0, 0}, {0, 0, 0, 0}, {0, 1, 0, 1}, {1, 0, 0, 0} });
        alphabet.put("й", new int[][]{ {0, 1, 1, 1, 0}, {1, 0, 1, 0, 1}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("й>", new int[][]{ {0, 1, 0, 0, 0}, {1, 0, 1, 1, 0}, {1, 0, 1, 1, 0}, {0, 1, 0, 0, 1}, {1, 0, 1, 1, 0} });
        alphabet.put("к", new int[][]{ {1, 0, 1}, {0, 1, 0}, {1, 1, 1} });
        alphabet.put("к>", new int[][]{ {0, 0, 1, 0}, {1, 0, 1, 1}, {0, 1, 0, 0}, {0, 0, 1, 0} });
        alphabet.put("л", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 0, 1} });
        alphabet.put("л>", new int[][]{ {1, 1, 1}, {0, 0, 1}, {0, 0, 1} });
        alphabet.put("м", new int[][]{ {1, 0, 0, 0, 1}, {1, 1, 0, 1, 1}, {1, 0, 1, 0, 1} });
        alphabet.put("м>", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 0, 0}, {1, 0, 1, 1, 1}, {0, 0, 0, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("н", new int[][]{ {1, 0, 1}, {1, 1, 1}, {1, 0, 1} });
        alphabet.put("н>", new int[][]{ {0, 1, 1, 0, 0}, {1, 1, 0, 0, 0}, {1, 0, 1, 0, 1}, {0, 0, 0, 1, 1}, {0, 0, 1, 1, 0} });
        alphabet.put("о", new int[][]{ {1, 1, 1}, {1, 0, 1}, {1, 1, 1} });
        alphabet.put("о>", new int[][]{ {0, 1, 0}, {1, 0, 1}, {0, 1, 0} });
        alphabet.put("п", new int[][]{ {1, 1, 1}, {1, 0, 1}, {1, 0, 1} });
        alphabet.put("п>", new int[][]{ {0, 1, 0, 0}, {1, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 1, 0} });
        alphabet.put("р", new int[][]{ {0, 0, 1, 1, 0, 0}, {0, 1, 0, 0, 1, 0}, {0, 1, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0} });
        alphabet.put("р>", new int[][]{ {0, 0, 1, 0}, {0, 1, 0, 1}, {1, 0, 1, 1} });
        alphabet.put("р>>", new int[][]{ {0, 1, 0, 0}, {1, 0, 1, 0}, {1, 1, 0, 1} });
        alphabet.put("р>>>", new int[][]{ {0, 1, 0, 0}, {1, 0, 1, 0}, {1, 1, 0, 1} });
        alphabet.put("с", new int[][]{ {0, 1, 0}, {1, 0, 0}, {0, 1, 0} });
        alphabet.put("с>", new int[][]{ {1, 1, 0, 0}, {1, 0, 0, 0}, {0, 1, 0, 1}, {0, 0, 1, 1} });
        alphabet.put("т", new int[][]{ {1, 1, 1}, {0, 1, 0} });
        alphabet.put("т>", new int[][]{ {1, 0, 0}, {0, 1, 0}, {1, 0, 1} });
        alphabet.put("у", new int[][]{ {1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("у>", new int[][]{ {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 1}, {1, 0, 0, 0} });
        alphabet.put("ф", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 0, 1, 0, 1}, {0, 1, 1, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("ф>", new int[][]{ {1, 1, 0, 1}, {1, 0, 1, 0}, {0, 1, 0, 1}, {0, 0, 1, 1} });
        alphabet.put("х", new int[][]{ {0, 1, 0}, {1, 1, 1}, {0, 1, 0} });
        alphabet.put("х>", new int[][]{ {1, 0, 1}, {0, 1, 0}, {1, 0, 1} });
        alphabet.put("ц", new int[][]{ {1, 0, 1}, {1, 1, 1}, {0, 1, 0} });
        alphabet.put("ц>", new int[][]{ {1, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 1, 1} });
        alphabet.put("ч", new int[][]{ {1, 1, 0, 1, 1}, {0, 1, 1, 1, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("ч>", new int[][]{ {0, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 1, 0, 1, 1}, {1, 0, 1, 1, 0} });
        alphabet.put("ш", new int[][]{ {0, 1, 0, 0, 0, 1, 0}, {0, 1, 0, 1, 0, 1, 0}, {1, 1, 1, 1, 1, 1, 1} });
        alphabet.put("ш>", new int[][]{ {0, 0, 1, 1, 0, 0}, {1, 1, 1, 0, 0, 0}, {0, 1, 0, 1, 0, 1}, {0, 0, 1, 0, 1, 1}, {0, 0, 0, 1, 1, 0}, {0, 0, 0, 0, 1, 0} });
        alphabet.put("щ", new int[][]{ {0, 1, 0, 0, 0, 1, 0}, {0, 1, 0, 1, 0, 1, 0}, {0, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 1, 0, 0, 0} });
        alphabet.put("щ>", new int[][]{ {0, 1, 1, 0, 0}, {1, 1, 0, 0, 0}, {1, 0, 1, 0, 1}, {0, 1, 0, 1, 1}, {1, 0, 1, 1, 0} });
        alphabet.put("ю", new int[][]{ {0, 1, 0}, {1, 0, 1}, {0, 1, 0}, {1, 1, 1} });
        alphabet.put("ю>", new int[][]{ {0, 0, 1, 1, 1}, {0, 0, 1, 0, 1}, {1, 0, 1, 1, 1}, {0, 1, 0, 0, 0}, {0, 0, 1, 0, 0} });
        alphabet.put("я", new int[][]{ {0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 0, 1, 0, 1} });
        alphabet.put("я>", new int[][]{ {1, 1, 1}, {0, 1, 1}, {1, 0, 1} });
        alphabet.put("ь", new int[][]{ {1, 1, 1}, {1, 0, 1}, {0, 1, 0}, {1, 0, 1} });
        alphabet.put("ь>", new int[][]{ {0, 1, 1, 1}, {0, 1, 0, 1}, {1, 1, 1, 1}, {0, 0, 1, 0} });
    }

    public int[][] renderWord(String word) {
        int gridSize = 60;
        int[][] canvasGrid = new int[gridSize][gridSize];

        if (word == null || word.isEmpty()) {
            return canvasGrid;
        }

        word = word.toLowerCase();

        String firstLetter = String.valueOf(word.charAt(0));
        int[][] firstMatrix = alphabet.get(firstLetter);

        if (firstMatrix == null) return canvasGrid;

        int w = firstMatrix[0].length;
        int h = firstMatrix.length;

        int pAttr = (firstLetter.equals("б") || firstLetter.equals("в")) ? 2 : 1;

        int rootW = w + 2 * h - 2 * pAttr;
        int rootH = 2 * h + w - 2 * pAttr;

        int currentX = (gridSize / 2) - (rootW / 2);
        int currentY = (gridSize / 2) - (rootH / 2);

        boolean isRed;
        int firstColorCode = 1;

        int[][] m0 = firstMatrix;
        int[][] m90 = rotateMatrix(m0);
        int[][] m180 = rotateMatrix(m90);
        int[][] m270 = rotateMatrix(m180);

        drawLetterMatrix(canvasGrid, m0, currentX + (rootW - w) / 2, currentY, firstColorCode);
        drawLetterMatrix(canvasGrid, m270, currentX, currentY + h - pAttr, firstColorCode);
        drawLetterMatrix(canvasGrid, m90, currentX + rootW - h, currentY + h - pAttr, firstColorCode);
        drawLetterMatrix(canvasGrid, m180, currentX + (rootW - w) / 2, currentY + h + w - 2 * pAttr, firstColorCode);

        isRed = false;

        Box currentBox = new Box();
        currentBox.x = currentX;
        currentBox.y = currentY;
        currentBox.width = rootW;
        currentBox.height = rootH;

        for (int i = 1; i < word.length(); i++) {
            String letterStr = String.valueOf(word.charAt(i));
            int currentColorCode = isRed ? 1 : 2;

            if (i % 2 != 0) {
                int[][] matrix = alphabet.get(letterStr + ">");
                if (matrix != null) {
                    currentBox = drawDiagonalsAndGrowBox(canvasGrid, matrix, currentBox, currentColorCode);
                }
            } else {
                int[][] matrix = alphabet.get(letterStr);
                if (matrix != null) {
                    currentBox = drawCross(canvasGrid, matrix, currentBox, currentColorCode);
                }
            }
            isRed = !isRed;
        }

        return canvasGrid;
    }

    private boolean intersect(int[][] canvasGrid, int[][] matrix, int startX, int startY) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] != 0) {
                    int targetX = startX + x;
                    int targetY = startY + y;
                    if (targetX >= 0 && targetX < 60 && targetY >= 0 && targetY < 60) {
                        if (canvasGrid[targetY][targetX] != 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Box drawDiagonalsAndGrowBox(int[][] canvasGrid, int[][] originalMatrix, Box box, int colorCode) {
        int[][] mTR = originalMatrix;
        int[][] mBR = rotateMatrix(mTR);
        int[][] mBL = rotateMatrix(mBR);
        int[][] mTL = rotateMatrix(mBL);

        int offset = 0;
        while (intersect(canvasGrid, mTL, box.x - offset, box.y - offset)) {
            offset++;
        }

        drawLetterMatrix(canvasGrid, mTL, box.x - offset, box.y - offset, colorCode);
        drawLetterMatrix(canvasGrid, mTR, box.x + box.width - mTR[0].length + offset, box.y - offset, colorCode);
        drawLetterMatrix(canvasGrid, mBR, box.x + box.width - mBR[0].length + offset, box.y + box.height - mBR.length + offset, colorCode);
        drawLetterMatrix(canvasGrid, mBL, box.x - offset, box.y + box.height - mBL.length + offset, colorCode);

        Box nextBox = new Box();
        nextBox.x = box.x - offset;
        nextBox.y = box.y - offset;
        nextBox.width = box.width + 2 * offset;
        nextBox.height = box.height + 2 * offset;

        return nextBox;
    }

    private Box drawCross(int[][] canvasGrid, int[][] originalMatrix, Box box, int colorCode) {
        int[][] m0 = originalMatrix;
        int[][] m90 = rotateMatrix(m0);
        int[][] m180 = rotateMatrix(m90);
        int[][] m270 = rotateMatrix(m180);

        int offset = 0;
        int startY_Left = box.y + (box.height - m270.length) / 2;

        while (intersect(canvasGrid, m270, box.x - offset, startY_Left)) {
            offset++;
        }

        drawLetterMatrix(canvasGrid, m270, box.x - offset, startY_Left, colorCode);
        drawLetterMatrix(canvasGrid, m90, box.x + box.width - m90[0].length + offset, box.y + (box.height - m90.length) / 2, colorCode);
        drawLetterMatrix(canvasGrid, m0, box.x + (box.width - m0[0].length) / 2, box.y - offset, colorCode);
        drawLetterMatrix(canvasGrid, m180, box.x + (box.width - m180[0].length) / 2, box.y + box.height - m180.length + offset, colorCode);

        return box;
    }

    private int[][] rotateMatrix(int[][] matrix) {
        int oldHeight = matrix.length;
        int oldWidth = matrix[0].length;

        int[][] newMatrix = new int[oldWidth][oldHeight];

        for (int y = 0; y < oldHeight; y++) {
            for (int x = 0; x < oldWidth; x++) {
                int val = matrix[y][x];
                int newX = (oldHeight - 1) - y;
                newMatrix[x][newX] = val;
            }
        }
        return newMatrix;
    }

    private void drawLetterMatrix(int[][] canvasGrid, int[][] matrix, int startX, int startY, int mainColorCode) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                int cellValue = matrix[y][x];

                int targetX = startX + x;
                int targetY = startY + y;

                if (targetX >= 0 && targetX < 60 && targetY >= 0 && targetY < 60) {
                    if (cellValue == 1) {
                        canvasGrid[targetY][targetX] = mainColorCode;
                    } else if (cellValue == 2) {
                        canvasGrid[targetY][targetX] = 2;
                    }
                }
            }
        }
    }
}