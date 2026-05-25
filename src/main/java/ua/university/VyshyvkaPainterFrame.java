package ua.university;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.File;
import java.util.Optional;

public class VyshyvkaPainterFrame extends JFrame {
    private final int btnWidth = 45;
    private final int btnHeight = 45;
    private final int canvasSize = 600;

    private final VyshyvkaRenderer renderer = new VyshyvkaRenderer();
    private final DrawingCanvas canvas = new DrawingCanvas();

    private JPanel sidePanel;
    private JTextField nameInput;

    private JButton btnColor1;
    private JButton btnColor2;
    private JButton btnEraser;
    private JButton btnCustom;
    private JButton btnBrushSize;
    private JButton btnUndo;

    public VyshyvkaPainterFrame() {
        super("Vyshyvka Painter by Ivan Bozhenko");
        initWindow();
    }

    private void initWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        nameInput = new JTextField(15);

        setupSidePanel();
        setupMenuBar();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(canvasSize, canvasSize));
        canvas.setBounds(0, 0, canvasSize, canvasSize);
        layeredPane.add(canvas, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
        add(setupBottomPanel(), BorderLayout.SOUTH);

        setupCustomCursor("assets/needle_2.png", new Point(1, 63), 64, 64);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void startInitialAnimation(String text) {
        int[][] grid = renderer.renderWord(text);
        canvas.animateGrid(grid);
    }

    private void setupSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidePanel.setBackground(new Color(240, 240, 240, 220));
        sidePanel.setOpaque(true);

        initButtons();

        JButton[] toolBarBtns = {btnColor1, btnColor2, btnCustom, btnEraser, btnBrushSize};
        for (JButton b : toolBarBtns) {
            sidePanel.add(b);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        int sideWidth = 80;
        sidePanel.setBounds(canvasSize - sideWidth, 0, sideWidth, canvasSize);
        sidePanel.setVisible(false);
    }

    private ImageIcon loadAndScaleIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private JButton createToolButton(Icon icon, Optional<String> toolTipText) {
        JButton btn = new JButton(icon);
        btn.setMaximumSize(new Dimension(45, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        toolTipText.ifPresent(btn::setToolTipText);
        return btn;
    }

    private ImageIcon createColorIcon(Color color, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(5, 5, width - 10, height - 10);
        g2.dispose();
        return new ImageIcon(img);
    }

    private void initButtons() {
        btnColor1 = createToolButton(createColorIcon(Color.RED, btnWidth, btnHeight), Optional.empty());
        btnColor1.setForeground(Color.RED);

        btnColor2 = createToolButton(createColorIcon(Color.BLACK, btnWidth, btnHeight), Optional.empty());
        btnColor2.setForeground(Color.BLACK);

        btnEraser = createToolButton(loadAndScaleIcon("assets/eraser.png", btnWidth, btnHeight), Optional.of("Гумка"));
        btnCustom = createToolButton(loadAndScaleIcon("assets/colour-palette.png", btnWidth, btnHeight), Optional.of("Свій колір"));
        btnBrushSize = createToolButton(loadAndScaleIcon("assets/needle-size.png", btnWidth, btnHeight), Optional.of("Розмір кисті"));

        btnUndo = createToolButton(loadAndScaleIcon("assets/undo.png", 18, 18), Optional.of("Назад"));

        setActiveTool(btnColor1);

        btnColor1.addActionListener(_ -> {
            canvas.setCurrentBrushColor(btnColor1.getForeground().getRGB());
            setActiveTool(btnColor1);
            setupCustomCursor("assets/cursor.png", new Point(25, 50), 64, 64);
        });

        btnColor2.addActionListener(_ -> {
            canvas.setCurrentBrushColor(btnColor2.getForeground().getRGB());
            setActiveTool(btnColor2);
            setupCustomCursor("assets/cursor.png", new Point(25, 50), 64, 64);
        });

        btnEraser.addActionListener(_ -> {
            canvas.setCurrentBrushColor(0);
            setActiveTool(btnEraser);
            setupCustomCursor("assets/eraser-cursor.png", new Point(0, 25), 32, 32);
        });

        btnUndo.addActionListener(_ -> canvas.undo());

        btnCustom.addActionListener(_ -> {
            Color selectedColor = JColorChooser.showDialog(this, "Оберіть колір", btnColor1.getForeground());
            if (selectedColor != null) {
                Color oldFirstColor = btnColor1.getForeground();
                btnColor2.setForeground(oldFirstColor);
                btnColor2.setIcon(createColorIcon(oldFirstColor, btnWidth, btnHeight));

                btnColor1.setForeground(selectedColor);
                btnColor1.setIcon(createColorIcon(selectedColor, btnWidth, btnHeight));

                canvas.setCurrentBrushColor(selectedColor.getRGB());

                setActiveTool(btnColor1);
                setupCustomCursor("assets/cursor.png", new Point(25, 50), 64, 64);
            }
        });

        btnBrushSize.addActionListener(_ -> {
            btnBrushSize.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            String input = JOptionPane.showInputDialog(this, "Введіть розмір:", "3");
            btnBrushSize.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            if (input != null && !input.isEmpty()) {
                try {
                    canvas.setBrushSize(Integer.parseInt(input));
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Помилка!");
                }
            }
        });
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JButton toggleMenu = new JButton("☰ Інструменти");
        toggleMenu.setFocusPainted(false);
        toggleMenu.addActionListener(_ -> sidePanel.setVisible(!sidePanel.isVisible()));
        menuBar.add(toggleMenu);

        JMenu menuFile = new JMenu("Файл");
        JMenuItem itemSave = new JMenuItem("Експорт в PNG");
        JMenuItem itemImport = new JMenuItem("Імпорт малюнка");

        itemSave.addActionListener(_ -> {
            JFileChooser saver = new JFileChooser();
            String fileName = "vyshyvka-" + (nameInput.getText().trim().isEmpty() ? "pattern" : nameInput.getText().trim());
            saver.setSelectedFile(new File(fileName + ".png"));
            if (saver.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = saver.getSelectedFile();
                    if (!file.getName().toLowerCase().endsWith(".png")) file = new File(file.getAbsolutePath() + ".png");
                    ImageIO.write(canvas.exportToImage(), "PNG", file);
                    JOptionPane.showMessageDialog(this, "Збережено!");
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        itemImport.addActionListener(_ -> {
            JFileChooser loader = new JFileChooser();
            if (loader.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage img = ImageIO.read(loader.getSelectedFile());
                    canvas.importFromImage(img);
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        menuFile.add(itemSave);
        menuFile.add(itemImport);

        JMenu menuActions = new JMenu("Дії");
        JCheckBoxMenuItem miMirrorX = new JCheckBoxMenuItem("Дзеркало по X");
        JCheckBoxMenuItem miMirrorY = new JCheckBoxMenuItem("Дзеркало по Y");
        JMenuItem miFlipX = new JMenuItem("Відобразити горизонтально");
        JMenuItem miFlipY = new JMenuItem("Відобразити вертикально");
        JMenuItem miClear = new JMenuItem("Очистити все");

        miMirrorX.addActionListener(_ -> canvas.setMirrorX(miMirrorX.isSelected()));
        miMirrorY.addActionListener(_ -> canvas.setMirrorY(miMirrorY.isSelected()));
        miFlipX.addActionListener(_ -> canvas.flipHorizontal());
        miFlipY.addActionListener(_ -> canvas.flipVertical());
        miClear.addActionListener(_ -> canvas.clearCanvas());

        menuActions.add(miMirrorX);
        menuActions.add(miMirrorY);
        menuActions.addSeparator();
        menuActions.add(miFlipX);
        menuActions.add(miFlipY);
        menuActions.addSeparator();
        menuActions.add(miClear);

        menuBar.add(menuFile);
        menuBar.add(menuActions);

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(btnUndo);

        setJMenuBar(menuBar);
    }

    private JPanel setupBottomPanel() {
        JPanel bottomPanel = new JPanel();
        JButton generateBtn = new JButton("Вишити");

        generateBtn.addActionListener(_ -> {
            int[][] newGrid = renderer.renderWord(nameInput.getText());
            canvas.animateGrid(newGrid);
        });

        bottomPanel.add(new JLabel("Ім'я:"));
        bottomPanel.add(nameInput);
        bottomPanel.add(generateBtn);

        return bottomPanel;
    }

    private void setupCustomCursor(String pathfile, Point hotspot, int width, int height) {
        ImageIcon cursorImage = new ImageIcon(pathfile);
        Image cursorImageScaled = cursorImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor customCursor = toolkit.createCustomCursor(cursorImageScaled, hotspot, "MyCursorName");
        canvas.setCursor(customCursor);
    }

    private void setActiveTool(JButton activeBtn) {
        JButton[] tools = {btnColor1, btnColor2, btnEraser, btnCustom};

        Border inactiveBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border activeBorder = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        for (JButton btn : tools) {
            btn.setBorder(inactiveBorder);
        }
        activeBtn.setBorder(activeBorder);
    }
}