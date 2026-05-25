package ua.university;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class DrawingCanvas extends JPanel {
    private static final int gridSize = 60;
    private static final int cellSize = 10;
    private final Image backgroundImage;

    private final Deque<int[][]> undoHistory = new ArrayDeque<>();
    private static final int maxUndoSteps = 50;
    private int[][] grid = new int[gridSize][gridSize];
    private Timer animationTimer;

    private int currentBrushColor = Color.RED.getRGB();
    private int currentBrushSize = 1;

    private boolean mirrorX = false;
    private boolean mirrorY = false;

    public DrawingCanvas() {
        setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));

        try {
            backgroundImage = ImageIO.read(new File("assets/bg-1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                saveState();
                handleMouseInput(e.getX(), e.getY());
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseInput(e.getX(), e.getY());
            }
        };
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setMirrorX(boolean val) { this.mirrorX = val; }
    public void setMirrorY(boolean val) { this.mirrorY = val; }
    public void setBrushSize(int size) { this.currentBrushSize = size; }
    public void setCurrentBrushColor(int color) { this.currentBrushColor = color; }

    private void handleMouseInput(int mouseX, int mouseY) {
        int cx = mouseX / cellSize;
        int cy = mouseY / cellSize;
        int offset = currentBrushSize / 2;

        for (int dy = -offset; dy <= offset; dy++) {
            for (int dx = -offset; dx <= offset; dx++) {
                applyPaint(cx + dx, cy + dy);
            }
        }
        repaint();
    }

    private void applyPaint(int x, int y) {
        drawSafe(x, y);
        if (mirrorX) drawSafe(gridSize - 1 - x, y);
        if (mirrorY) drawSafe(x, gridSize - 1 - y);
        if (mirrorX && mirrorY) drawSafe(gridSize - 1 - x, gridSize - 1 - y);
    }

    private void drawSafe(int x, int y) {
        if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
            grid[y][x] = currentBrushColor;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] != 0) {
                    g2d.setColor(new Color(grid[y][x]));
                    int cellX = x * cellSize;
                    int cellY = y * cellSize;

                    int padding = 1;

                    g2d.drawLine(cellX + padding, cellY + padding,
                            cellX + cellSize - padding, cellY + cellSize - padding);

                    g2d.drawLine(cellX + cellSize - padding, cellY + padding,
                            cellX + padding, cellY + cellSize - padding);
                }
            }
        }
        g2d.setColor(new Color( 0, 0, 0, 20));
        for (int i = 0; i <= gridSize; i++) {
            g2d.drawLine(i * cellSize, 0, i * cellSize, gridSize * cellSize);
            g2d.drawLine(0, i * cellSize, gridSize * cellSize, i * cellSize);
        }
    }

    public void clearCanvas() {
        this.grid = new int[gridSize][gridSize];
        repaint();
    }

    public BufferedImage exportToImage() {
        BufferedImage output = new BufferedImage(gridSize * cellSize, gridSize * cellSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = output.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, output.getWidth(), output.getHeight());

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] != 0) {
                    g2.setColor(new Color(grid[y][x]));
                    g2.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
        g2.dispose();
        return output;
    }

    public void importFromImage(BufferedImage img) {
        int[][] newGrid = new int[gridSize][gridSize];

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                int pixelX = x * 10 + 5;
                int pixelY = y * 10 + 5;

                int rgb = img.getRGB(pixelX, pixelY);
                Color c = new Color(rgb, true);

                if (c.getAlpha() > 10 && (c.getRed() < 245 || c.getGreen() < 245 || c.getBlue() < 245)) {
                    newGrid[y][x] = rgb;
                } else {
                    newGrid[y][x] = 0;
                }
            }
        }
       this.animateGrid(newGrid);
    }

    public void animateGrid(int[][] targetGrid) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        this.grid = new int[gridSize][gridSize];

        ArrayList<Point> pointList = new ArrayList<>();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                pointList.add(new Point(x, y));
            }
        }

        int centreX = gridSize / 2;
        int centreY = gridSize / 2;

        pointList.sort((p1, p2) -> {
            double dist1 = Math.pow(p1.x - centreX, 2) + Math.pow(p1.y - centreY, 2);
            double dist2 = Math.pow(p2.x - centreX, 2) + Math.pow(p2.y - centreY, 2);
            return Double.compare(dist1, dist2);
        });

        final int[] currentIndex = {0};
        int totalPoints = pointList.size();

        animationTimer = new Timer(10, e -> {
            for (int i = 0; i < 5; i++) {
                if (currentIndex[0] < totalPoints) {
                    Point p = pointList.get(currentIndex[0]);
                    int x = p.x;
                    int y = p.y;

                    int code = targetGrid[y][x];

                    switch (code) {
                        case 1 -> grid[y][x] = Color.RED.getRGB();
                        case 2 -> grid[y][x] = Color.BLACK.getRGB();
                        default -> grid[y][x] = code;
                    }

                    currentIndex[0]++;
                } else {
                    ((Timer)e.getSource()).stop();
                    break;
                }
            }
            repaint();
        });

        animationTimer.start();
    }

    public void saveState() {
        int[][] copy =  new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, gridSize);
        }
        undoHistory.addFirst(copy);
        if (undoHistory.size() > maxUndoSteps) {
            undoHistory.removeLast();
        }
    }

    public void undo() {
        if (!undoHistory.isEmpty()) {
            this.grid = undoHistory.removeFirst();
            repaint();
        }
    }

    public void flipHorizontal() {
        saveState();
        int maxX = -1;

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] != 0 && x > maxX) {
                    maxX = x;
                }
            }
        }

        if (maxX == -1) return;

        int[][] newGrid = new int[gridSize][gridSize];

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] != 0) {
                    newGrid[y][x] = grid[y][x];
                    int mirrorX = maxX + 1 + (maxX - x);
                    if (mirrorX >= 0 && mirrorX < gridSize) {
                        newGrid[y][mirrorX] = grid[y][x];
                    }
                }
            }
        }
        this.grid = newGrid;
        repaint();
    }

    public void flipVertical() {
        saveState();
        int maxY = -1;

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] != 0 && y > maxY) {
                    maxY = y;
                }
            }
        }

        if (maxY == -1) return;

        int[][] newGrid = new int[gridSize][gridSize];

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] != 0) {
                    newGrid[y][x] = grid[y][x];
                    int mirrorY = maxY + 1 + (maxY - y);
                    if (mirrorY >= 0 && mirrorY < gridSize) {
                        newGrid[mirrorY][x] = grid[y][x];
                    }
                }
            }
        }
        this.grid = newGrid;
        repaint();
    }
}