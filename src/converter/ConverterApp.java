package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ConverterApp extends JFrame {

    public ConverterApp() {
        setTitle("Универсальный Конвертер");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 600);
        setLocationRelativeTo(null);

        java.net.URL iconURL = getClass().getResource("/icon.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        }

        initMenuBar();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 14));

        tabbedPane.addTab("Длина", createPanel(new LengthConverter()));
        tabbedPane.addTab("Вес", createPanel(new WeightConverter()));
        tabbedPane.addTab("Валюта", createPanel(new CurrencyConverter()));
        tabbedPane.addTab("Температура", createPanel(new TemperatureConverter()));
        tabbedPane.addTab("Данные", createPanel(new DataConverter()));

        add(tabbedPane);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu programMenu = new JMenu("Программа");

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> System.exit(0));
        programMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Помощь");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Курсовой проект\nТема: Конвертер величин",
                "О программе", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(programMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private JPanel createPanel(Converter converter) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel inputLabel = new JLabel("Значение:");
        JTextField inputField = new JTextField(10);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    if (!inputField.getText().isEmpty()) {
                        Double.parseDouble(inputField.getText().replace(",", "."));
                        inputField.setBackground(UIManager.getColor("TextField.background"));
                    }
                } catch (NumberFormatException ex) {
                    inputField.setBackground(new Color(255, 200, 200));
                }
            }
        });

        JComboBox<String> fromBox = new JComboBox<>(converter.getUnitNames());
        JComboBox<String> toBox = new JComboBox<>(converter.getUnitNames());

        JButton swapButton = new JButton("⇅");
        swapButton.setToolTipText("Поменять местами");
        swapButton.addActionListener(e -> {
            int fromIndex = fromBox.getSelectedIndex();
            int toIndex = toBox.getSelectedIndex();
            fromBox.setSelectedIndex(toIndex);
            toBox.setSelectedIndex(fromIndex);
        });

        JButton convertButton = new JButton("Рассчитать");
        convertButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel resultLabel = new JLabel("Результат: 0.0");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        resultLabel.setForeground(new Color(0, 102, 204));

        JCheckBox saveCheck = new JCheckBox("Записывать в лог", true);
        JButton clearHistoryBtn = new JButton("Очистить лог");

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(inputLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(inputField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Из:"), gbc);
        gbc.gridx = 1;
        formPanel.add(fromBox, gbc);

        gbc.gridx = 2;
        formPanel.add(swapButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("В:"), gbc);
        gbc.gridx = 1;
        formPanel.add(toBox, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(convertButton, gbc);

        gbc.gridy = 4;
        formPanel.add(resultLabel, gbc);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(saveCheck);
        bottomPanel.add(clearHistoryBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        convertButton.addActionListener(e -> {
            try {
                String text = inputField.getText();
                if (text.isEmpty()) return;

                double val = Double.parseDouble(text.replace(",", "."));
                String f = (String) fromBox.getSelectedItem();
                String t = (String) toBox.getSelectedItem();

                double res = converter.convert(val, f, t);

                DecimalFormat df = new DecimalFormat("#.#########");
                String resultText = df.format(res);
                resultLabel.setText("Результат: " + resultText);

                if (saveCheck.isSelected()) {
                    HistoryLogger.saveHistory(val + " " + f + " -> " + resultText + " " + t);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ВОТ ЗДЕСЬ ИЗМЕНЕНИЕ: убраны фигурные скобки { }
        clearHistoryBtn.addActionListener(e -> HistoryLogger.clearHistory());

        return mainPanel;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {

        }

        SwingUtilities.invokeLater(() -> new ConverterApp().setVisible(true));
    }
}