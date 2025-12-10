package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Properties;

public class ConverterApp extends JFrame {
    private boolean isDarkTheme = false;
    private boolean saveSettings = true;
    private JTabbedPane tabbedPane;
    private int lastSelectedTab = 0;

    public ConverterApp() {
        applySettings(SettingsManager.loadSettings());
        initUI();
    }

    private void applySettings(Properties props) {
        Lang.isRu = "ru".equals(props.getProperty("lang", "ru"));
        isDarkTheme = Boolean.parseBoolean(props.getProperty("dark", "false"));
        saveSettings = true;
        setAlwaysOnTop(Boolean.parseBoolean(props.getProperty("top", "false")));
        try {
            lastSelectedTab = Integer.parseInt(props.getProperty("tab", "0"));
        } catch (NumberFormatException e) {
            lastSelectedTab = 0;
        }
    }

    private void initUI() {
        setupTheme();

        getContentPane().removeAll();
        setTitle(Lang.get("title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 720);
        setLocationRelativeTo(null);

        java.net.URL iconURL = getClass().getResource("/icon.png");
        if (iconURL != null) setIconImage(new ImageIcon(iconURL).getImage());

        initMenuBar();

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 14));

        addTabs();

        if (lastSelectedTab >= 0 && lastSelectedTab < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(lastSelectedTab);
        }

        add(tabbedPane);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (saveSettings) {
                    SettingsManager.saveSettings(Lang.isRu, isDarkTheme, isAlwaysOnTop(), tabbedPane.getSelectedIndex());
                }
            }
        });

        SwingUtilities.updateComponentTreeUI(this);
        revalidate();
        repaint();
    }

    private void setupTheme() {
        if (isDarkTheme) {
            UIManager.put("control", new Color(40, 40, 40));
            UIManager.put("info", new Color(40, 40, 40));
            UIManager.put("nimbusBase", new Color(18, 30, 49));
            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", new Color(40, 40, 40));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 7));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
            UIManager.put("text", new Color(230, 230, 230));

            UIManager.put("TextField.background", new Color(60, 60, 60));
            UIManager.put("TextField.foreground", Color.WHITE);
            UIManager.put("TextField.caretForeground", Color.WHITE);
            UIManager.put("ComboBox.background", new Color(60, 60, 60));
            UIManager.put("ComboBox.foreground", Color.WHITE);
            UIManager.put("Button.background", new Color(60, 60, 60));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Label.foreground", Color.WHITE);
            UIManager.put("Panel.background", new Color(40, 40, 40));
            UIManager.put("OptionPane.background", new Color(40, 40, 40));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
        } else {
            UIManager.put("control", null);
            UIManager.put("text", null);
            UIManager.put("nimbusBase", null);
            UIManager.put("nimbusLightBackground", null);
            UIManager.put("TextField.background", null);
            UIManager.put("TextField.foreground", null);
            UIManager.put("TextField.caretForeground", null);
            UIManager.put("ComboBox.background", null);
            UIManager.put("ComboBox.foreground", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Label.foreground", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.background", null);
            UIManager.put("OptionPane.messageForeground", null);
        }

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void addTabs() {
        tabbedPane.addTab(Lang.get("tab_len"), createPanel(new LengthConverter(Lang.isRu)));
        tabbedPane.addTab(Lang.get("tab_wgt"), createPanel(new WeightConverter(Lang.isRu)));
        tabbedPane.addTab(Lang.get("tab_cur"), createPanel(new CurrencyConverter()));
        tabbedPane.addTab(Lang.get("tab_tmp"), createPanel(new TemperatureConverter(Lang.isRu)));
        tabbedPane.addTab(Lang.get("tab_dat"), createPanel(new DataConverter(Lang.isRu)));
        tabbedPane.addTab(Lang.get("tab_tim"), createPanel(new TimeConverter(Lang.isRu)));
        tabbedPane.addTab(Lang.get("tab_spd"), createPanel(new SpeedConverter(Lang.isRu)));
        tabbedPane.addTab(Lang.get("tab_area"), createPanel(new AreaConverter(Lang.isRu)));
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        if (isDarkTheme) {
            menuBar.setBackground(new Color(30, 30, 30));
            menuBar.setBorder(new EmptyBorder(0,0,0,0));
        }

        JMenu programMenu = createStyledMenu(Lang.get("menu_prog"));
        JMenuItem exitItem = createStyledMenuItem(Lang.get("menu_exit"));
        exitItem.addActionListener(e -> System.exit(0));
        programMenu.add(exitItem);

        JMenu langMenu = createStyledMenu(Lang.get("menu_lang"));
        JMenuItem ruItem = createStyledMenuItem("Русский");
        JMenuItem enItem = createStyledMenuItem("English");

        ruItem.addActionListener(e -> changeLanguage(true));
        enItem.addActionListener(e -> changeLanguage(false));

        langMenu.add(ruItem);
        langMenu.add(enItem);
        menuBar.add(programMenu);
        menuBar.add(langMenu);
        setJMenuBar(menuBar);
    }

    private JMenu createStyledMenu(String text) {
        JMenu menu = new JMenu(text);
        if (isDarkTheme) {
            menu.setForeground(Color.WHITE);
        }
        return menu;
    }

    private JMenuItem createStyledMenuItem(String text) {
        return new JMenuItem(text);
    }

    private void changeLanguage(boolean isRu) {
        Lang.isRu = isRu;
        lastSelectedTab = tabbedPane.getSelectedIndex();
        initUI();
    }

    private JPanel createPanel(Converter converter) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel inputLabel = new JLabel(Lang.get("lbl_val"));
        JTextField inputField = new JTextField(12);
        JComboBox<String> fromBox = new JComboBox<>(converter.getUnitNames());
        JComboBox<String> toBox = new JComboBox<>(converter.getUnitNames());
        JButton swapButton = new JButton("⇅");
        JLabel resultLabel = new JLabel(Lang.get("lbl_res") + "0.0");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        resultLabel.setForeground(new Color(0, 102, 204));
        if (isDarkTheme) resultLabel.setForeground(new Color(100, 180, 255));

        JButton copyBtn = new JButton(Lang.get("btn_copy"));
        copyBtn.setMargin(new Insets(2, 5, 2, 5));

        JCheckBox saveCheck = new JCheckBox(Lang.get("chk_log"), true);
        JCheckBox alwaysTopCheck = new JCheckBox(Lang.get("chk_top"), isAlwaysOnTop());
        JCheckBox darkThemeCheck = new JCheckBox(Lang.get("chk_dark"), isDarkTheme);
        JCheckBox saveSettingsCheck = new JCheckBox(Lang.get("chk_save"), saveSettings);

        if (isDarkTheme) {
            Color darkBg = new Color(40, 40, 40);
            Color whiteFg = Color.WHITE;
            saveCheck.setBackground(darkBg); saveCheck.setForeground(whiteFg);
            alwaysTopCheck.setBackground(darkBg); alwaysTopCheck.setForeground(whiteFg);
            darkThemeCheck.setBackground(darkBg); darkThemeCheck.setForeground(whiteFg);
            saveSettingsCheck.setBackground(darkBg); saveSettingsCheck.setForeground(whiteFg);
        }

        Runnable calculate = () -> {
            try {
                String text = inputField.getText();
                if (text.isEmpty() || text.equals("-")) return;
                double val = Double.parseDouble(text.replace(",", "."));
                String f = (String) fromBox.getSelectedItem();
                String t = (String) toBox.getSelectedItem();

                double res = converter.convert(val, f, t);

                DecimalFormat df = new DecimalFormat("#.###");
                String resStr = df.format(res);
                resultLabel.setText(Lang.get("lbl_res") + resStr);

                if (saveCheck.isSelected()) {
                    HistoryLogger.saveHistory(val + " " + f + " -> " + resStr + " " + t);
                }
            } catch (Exception ignored) {
            }
        };

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculate.run(); }
            public void removeUpdate(DocumentEvent e) { calculate.run(); }
            public void changedUpdate(DocumentEvent e) { calculate.run(); }
        });

        ActionListener recalcListener = e -> calculate.run();
        fromBox.addActionListener(recalcListener);
        toBox.addActionListener(recalcListener);

        swapButton.addActionListener(e -> {
            int fromIndex = fromBox.getSelectedIndex();
            int toIndex = toBox.getSelectedIndex();
            fromBox.setSelectedIndex(toIndex);
            toBox.setSelectedIndex(fromIndex);
            calculate.run();
        });

        copyBtn.addActionListener(e -> {
            String text = resultLabel.getText().replace(Lang.get("lbl_res"), "");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        });

        alwaysTopCheck.addActionListener(e -> setAlwaysOnTop(alwaysTopCheck.isSelected()));

        darkThemeCheck.addActionListener(e -> {
            isDarkTheme = darkThemeCheck.isSelected();
            lastSelectedTab = tabbedPane.getSelectedIndex();
            initUI();
        });

        saveSettingsCheck.addActionListener(e -> saveSettings = saveSettingsCheck.isSelected());

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(inputLabel, gbc);
        gbc.gridx = 1; formPanel.add(inputField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel(Lang.get("lbl_from")), gbc);
        gbc.gridx = 1; formPanel.add(fromBox, gbc);
        gbc.gridx = 2; formPanel.add(swapButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel(Lang.get("lbl_to")), gbc);
        gbc.gridx = 1; formPanel.add(toBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel resPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resPanel.add(resultLabel);
        resPanel.add(copyBtn);
        formPanel.add(resPanel, gbc);

        JPanel settingsPanel = new JPanel(new GridLayout(2, 2));
        settingsPanel.add(saveCheck);
        settingsPanel.add(alwaysTopCheck);
        settingsPanel.add(darkThemeCheck);
        settingsPanel.add(saveSettingsCheck);

        gbc.gridy = 4; formPanel.add(settingsPanel, gbc);

        JPanel historyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton openLogBtn = new JButton(Lang.get("btn_open"));
        openLogBtn.addActionListener(e -> HistoryLogger.openLogFile());

        JButton viewLogBtn = new JButton(Lang.get("btn_view"));
        viewLogBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, HistoryLogger.getLastTenLines(), "History", JOptionPane.INFORMATION_MESSAGE));

        JButton clearLogBtn = new JButton(Lang.get("btn_clear"));
        clearLogBtn.addActionListener(e -> HistoryLogger.clearHistory());

        historyPanel.add(openLogBtn);
        historyPanel.add(viewLogBtn);
        historyPanel.add(clearLogBtn);

        if (converter instanceof CurrencyConverter) {
            JButton editRateBtn = new JButton(Lang.get("btn_edit"));
            if (isDarkTheme) {
                editRateBtn.setBackground(new Color(100, 80, 0));
                editRateBtn.setForeground(Color.WHITE);
            } else {
                editRateBtn.setBackground(new Color(255, 240, 200));
            }
            editRateBtn.addActionListener(e -> showCurrencyDialog(converter));
            historyPanel.add(editRateBtn);
        }

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(historyPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    private void showCurrencyDialog(Converter converter) {
        JPanel editPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JComboBox<String> currencySelector = new JComboBox<>(converter.getUnitNames());
        JTextField newRateField = new JTextField();

        editPanel.add(new JLabel(Lang.get("lbl_val")));
        editPanel.add(currencySelector);

        JLabel hintLabel = new JLabel(Lang.get("msg_hint"));
        hintLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        editPanel.add(hintLabel);
        editPanel.add(newRateField);

        int result = JOptionPane.showConfirmDialog(this, editPanel, Lang.get("btn_edit"), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String selected = (String) currencySelector.getSelectedItem();
                double userVal = Double.parseDouble(newRateField.getText().replace(",", "."));
                if (userVal <= 0) throw new NumberFormatException();

                ((BaseConverter) converter).setRate(selected, 1.0 / userVal);
                JOptionPane.showMessageDialog(this, Lang.get("msg_upd"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Invalid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConverterApp().setVisible(true));
    }
}