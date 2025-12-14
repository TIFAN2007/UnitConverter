package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.Serial;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ConverterApp extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean isAlwaysOnTop = false;
    private boolean isLogEnabled = true;
    private int decimalPrecision = 2;
    private int lastSelectedTab = 0;

    private JTabbedPane tabbedPane;
    private JComboBox<String> currencyFromBox;
    private Converter currentCurrencyConverter;

    public ConverterApp() {
        loadConfig();
        initFrameSettings();
        applyThemeAndRebuildUI();
    }

    private void loadConfig() {
        Properties props = SettingsManager.loadSettings();
        try {
            Lang.currentLang = Integer.parseInt(props.getProperty("lang", String.valueOf(Lang.RU)));
        } catch (NumberFormatException e) {
            Lang.currentLang = Lang.RU;
        }
        isAlwaysOnTop = Boolean.parseBoolean(props.getProperty("top", "false"));
        setAlwaysOnTop(isAlwaysOnTop);
        try {
            lastSelectedTab = Integer.parseInt(props.getProperty("tab", "0"));
        } catch (NumberFormatException e) { lastSelectedTab = 0; }
        try {
            decimalPrecision = Integer.parseInt(props.getProperty("prec", "2"));
        } catch (NumberFormatException e) { decimalPrecision = 2; }
    }

    private void saveConfig() {
        Map<String, Double> rates = new HashMap<>();
        if (currentCurrencyConverter instanceof BaseConverter baseConverter) {
            rates = baseConverter.getAllRates();
        }
        SettingsManager.saveSettings(Lang.currentLang, isAlwaysOnTop,
                tabbedPane != null ? tabbedPane.getSelectedIndex() : 0, decimalPrecision, rates);
    }

    private void initFrameSettings() {
        setTitle(Lang.get("title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setMinimumSize(new Dimension(600, 450));
        setLocationRelativeTo(null);
        java.net.URL iconURL = getClass().getResource("/icon.png");
        if (iconURL != null) setIconImage(new ImageIcon(iconURL).getImage());
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { saveConfig(); }
        });
    }

    private void applyThemeAndRebuildUI() {
        configureUIManager();
        getContentPane().removeAll();
        setJMenuBar(createMenuBar());
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(10, 10, 10, 10));
        fillTabs();
        if (lastSelectedTab >= 0 && lastSelectedTab < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(lastSelectedTab);
        }
        add(tabbedPane);
        SwingUtilities.updateComponentTreeUI(this);
        revalidate();
        repaint();
    }

    private void configureUIManager() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();

            Color bg = new Color(240, 240, 240);
            Color inputBg = Color.WHITE;
            Color fg = Color.BLACK;
            Color base = new Color(214, 217, 223);
            Color accent = new Color(0, 120, 215);

            ColorUIResource uiBg = new ColorUIResource(bg);
            ColorUIResource uiInputBg = new ColorUIResource(inputBg);
            ColorUIResource uiFg = new ColorUIResource(fg);
            ColorUIResource uiBase = new ColorUIResource(base);
            ColorUIResource uiAccent = new ColorUIResource(accent);

            defaults.put("nimbusBase", uiBase);
            defaults.put("nimbusBlueGrey", uiBase);
            defaults.put("control", uiBg);
            defaults.put("text", uiFg);
            defaults.put("nimbusLightBackground", uiInputBg);
            defaults.put("nimbusFocus", uiAccent);

            defaults.put("TextField.background", uiInputBg);
            defaults.put("TextField.foreground", uiFg);
            defaults.put("TextField.caretForeground", uiFg);
            defaults.put("FormattedTextField.background", uiInputBg);
            defaults.put("FormattedTextField.foreground", uiFg);
            defaults.put("FormattedTextField.caretForeground", uiFg);
            defaults.put("Spinner.background", uiInputBg);
            defaults.put("Spinner.foreground", uiFg);
            defaults.put("ComboBox.background", uiInputBg);
            defaults.put("ComboBox.foreground", uiFg);
            defaults.put("Button.background", uiBase);
            defaults.put("Button.foreground", uiFg);
            defaults.put("Panel.background", uiBg);
            defaults.put("TabbedPane.background", uiBase);
            defaults.put("TabbedPane.foreground", uiFg);
            defaults.put("TabbedPane.contentAreaColor", uiBg);
            defaults.put("TabbedPane.selected", uiInputBg);
            defaults.put("MenuBar.background", uiBg);
            defaults.put("Menu.background", uiBg);
            defaults.put("Menu.foreground", uiFg);
            defaults.put("MenuItem.background", uiBg);
            defaults.put("MenuItem.foreground", uiFg);
            defaults.put("OptionPane.background", uiBg);
            defaults.put("OptionPane.messageForeground", uiFg);

            if (Lang.currentLang == Lang.RU) {
                UIManager.put("OptionPane.okButtonText", "ОК");
                UIManager.put("OptionPane.cancelButtonText", "Отмена");
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
            } else {
                UIManager.put("OptionPane.okButtonText", "OK");
                UIManager.put("OptionPane.cancelButtonText", "Cancel");
                UIManager.put("OptionPane.yesButtonText", "Yes");
                UIManager.put("OptionPane.noButtonText", "No");
            }
        } catch (Exception ignored) {}
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu programMenu = new JMenu(Lang.get("menu_prog"));
        JMenuItem exitItem = new JMenuItem(Lang.get("menu_exit"));
        exitItem.addActionListener(e -> { saveConfig(); System.exit(0); });
        programMenu.add(exitItem);

        JMenu viewMenu = new JMenu(Lang.get("menu_view"));
        JCheckBoxMenuItem logCheck = new JCheckBoxMenuItem(Lang.get("chk_log"), isLogEnabled);
        logCheck.addActionListener(e -> { isLogEnabled = logCheck.isSelected(); saveConfig(); });
        JCheckBoxMenuItem topCheck = new JCheckBoxMenuItem(Lang.get("chk_top"), isAlwaysOnTop);
        topCheck.addActionListener(e -> { isAlwaysOnTop = topCheck.isSelected(); setAlwaysOnTop(isAlwaysOnTop); saveConfig(); });
        viewMenu.add(logCheck); viewMenu.add(topCheck);

        JMenu histMenu = new JMenu(Lang.get("menu_hist"));
        JMenuItem viewLast = new JMenuItem(Lang.get("act_view_last"));
        viewLast.addActionListener(e -> showHistoryWindow());
        JMenuItem openFile = new JMenuItem(Lang.get("act_open_file"));
        openFile.addActionListener(e -> HistoryLogger.openLogFile(this));
        JMenuItem clearHist = new JMenuItem(Lang.get("act_clear"));
        clearHist.addActionListener(e -> HistoryLogger.clearHistory(this));
        histMenu.add(viewLast); histMenu.add(openFile); histMenu.addSeparator(); histMenu.add(clearHist);

        JMenu currMenu = new JMenu(Lang.get("menu_curr"));
        JMenuItem editRateItem = new JMenuItem(Lang.get("act_edit_rate"));
        editRateItem.addActionListener(e -> showSmartCurrencyDialog());
        currMenu.add(editRateItem);

        JMenu langMenu = new JMenu(Lang.get("menu_lang"));
        JRadioButtonMenuItem ruItem = new JRadioButtonMenuItem("Русский", Lang.currentLang == Lang.RU);
        JRadioButtonMenuItem enItem = new JRadioButtonMenuItem("English", Lang.currentLang == Lang.EN);
        ButtonGroup bg = new ButtonGroup(); bg.add(ruItem); bg.add(enItem);
        ActionListener langListener = e -> {
            Lang.currentLang = e.getSource() == ruItem ? Lang.RU : Lang.EN;
            saveConfig();
            applyThemeAndRebuildUI();
        };
        ruItem.addActionListener(langListener); enItem.addActionListener(langListener);
        langMenu.add(ruItem); langMenu.add(enItem);

        menuBar.add(programMenu); menuBar.add(viewMenu); menuBar.add(histMenu); menuBar.add(currMenu); menuBar.add(langMenu);
        return menuBar;
    }

    private void fillTabs() {
        Map<String, Double> savedRates = SettingsManager.loadRates(SettingsManager.loadSettings());
        currentCurrencyConverter = new CurrencyConverter(savedRates);
        tabbedPane.addTab(Lang.get("tab_len"), createConverterPanel(new LengthConverter(Lang.currentLang), false));
        tabbedPane.addTab(Lang.get("tab_wgt"), createConverterPanel(new WeightConverter(Lang.currentLang), false));
        tabbedPane.addTab(Lang.get("tab_cur"), createConverterPanel(currentCurrencyConverter, true));
        tabbedPane.addTab(Lang.get("tab_tmp"), createConverterPanel(new TemperatureConverter(Lang.currentLang), false));
        tabbedPane.addTab(Lang.get("tab_dat"), createConverterPanel(new DataConverter(Lang.currentLang), false));
        tabbedPane.addTab(Lang.get("tab_tim"), createConverterPanel(new TimeConverter(Lang.currentLang), false));
        tabbedPane.addTab(Lang.get("tab_spd"), createConverterPanel(new SpeedConverter(Lang.currentLang), false));
        tabbedPane.addTab(Lang.get("tab_area"), createConverterPanel(new AreaConverter(Lang.currentLang), false));
    }

    private JPanel createConverterPanel(Converter converter, boolean isCurrency) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        JTextField inputField = new JTextField(15);
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputField.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) e.consume();
                if (c == '.' && inputField.getText().contains(".")) e.consume();
            }
        });

        JComboBox<String> fromBox = new JComboBox<>(converter.getUnitNames());
        JComboBox<String> toBox = new JComboBox<>(converter.getUnitNames());
        if (isCurrency) this.currencyFromBox = fromBox;

        JButton swapBtn = new JButton("⇅");
        JLabel resultLabel = new JLabel("0.0");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setForeground(new Color(0, 120, 215));

        JSpinner precSpinner = new JSpinner(new SpinnerNumberModel(decimalPrecision, 0, 10, 1));
        precSpinner.setBorder(null);

        precSpinner.setUI(new BasicSpinnerUI() {
            @Override protected Component createNextButton() {
                BasicArrowButton btn = new BasicArrowButton(SwingConstants.NORTH, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
                btn.setBackground(Color.WHITE);
                btn.setBorder(null);
                return btn;
            }
            @Override protected Component createPreviousButton() {
                BasicArrowButton btn = new BasicArrowButton(SwingConstants.SOUTH, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
                btn.setBackground(Color.WHITE);
                btn.setBorder(null);
                return btn;
            }
        });

        if (precSpinner.getEditor() instanceof JSpinner.DefaultEditor spinnerEditor) {
            spinnerEditor.getTextField().setColumns(3);
            spinnerEditor.getTextField().setBorder(null);
        }

        JButton saveLogBtn = new JButton(Lang.get("btn_calc"));
        JButton copyBtn = new JButton(Lang.get("btn_copy"));

        Runnable calculate = () -> {
            try {
                if (inputField.getText().isEmpty()) { resultLabel.setText("0.0"); return; }
                double val = Double.parseDouble(inputField.getText());
                double res = converter.convert(val, (String) fromBox.getSelectedItem(), (String) toBox.getSelectedItem());
                int prec = (int) precSpinner.getValue();
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                String pattern = (prec == 0) ? "#" : "#." + "#".repeat(prec);
                resultLabel.setText(new DecimalFormat(pattern, symbols).format(res));
            } catch (Exception ignored) {}
        };

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { calculate.run(); }
            @Override public void removeUpdate(DocumentEvent e) { calculate.run(); }
            @Override public void changedUpdate(DocumentEvent e) { calculate.run(); }
        });

        ActionListener recalcListener = e -> calculate.run();
        fromBox.addActionListener(recalcListener);
        toBox.addActionListener(recalcListener);
        swapBtn.addActionListener(e -> {
            int index = fromBox.getSelectedIndex();
            fromBox.setSelectedIndex(toBox.getSelectedIndex());
            toBox.setSelectedIndex(index);
        });
        precSpinner.addChangeListener(e -> {
            decimalPrecision = (int) precSpinner.getValue();
            saveConfig();
            calculate.run();
        });
        saveLogBtn.addActionListener(e -> {
            if (isLogEnabled && !inputField.getText().isEmpty()) {
                calculate.run();
                HistoryLogger.saveHistory(inputField.getText() + " " + fromBox.getSelectedItem() + " -> " + resultLabel.getText() + " " + toBox.getSelectedItem(), this);
            }
        });
        copyBtn.addActionListener(e -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(resultLabel.getText()), null));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        mainPanel.add(new JLabel(Lang.get("lbl_val")), gbc);
        gbc.gridy = 1; mainPanel.add(inputField, gbc);
        gbc.gridy = 2; gbc.gridwidth = 1;
        mainPanel.add(new JLabel(Lang.get("lbl_from")), gbc);
        gbc.gridx = 2; mainPanel.add(new JLabel(Lang.get("lbl_to")), gbc);
        gbc.gridy = 3; gbc.gridx = 0; mainPanel.add(fromBox, gbc);
        gbc.gridx = 1; gbc.weightx = 0.1; mainPanel.add(swapBtn, gbc);
        gbc.gridx = 2; gbc.weightx = 1.0; mainPanel.add(toBox, gbc);
        gbc.gridy = 4; gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        JPanel precPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        precPanel.setOpaque(false);
        precPanel.add(new JLabel(Lang.get("lbl_prec"))); precPanel.add(precSpinner);
        mainPanel.add(precPanel, gbc);
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(new JLabel(Lang.get("lbl_res"), SwingConstants.CENTER), gbc);
        gbc.gridy = 6; resultLabel.setHorizontalAlignment(SwingConstants.CENTER); mainPanel.add(resultLabel, gbc);
        gbc.gridy = 7; JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false); btnPanel.add(saveLogBtn); btnPanel.add(copyBtn);
        mainPanel.add(btnPanel, gbc);
        gbc.gridy = 8; gbc.weighty = 1.0; mainPanel.add(new JPanel(), gbc);

        return mainPanel;
    }

    private void showSmartCurrencyDialog() {
        if (!(currentCurrencyConverter instanceof BaseConverter baseC)) return;
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        JComboBox<String> unitBox = new JComboBox<>(baseC.getUnitNames());
        if (currencyFromBox != null) unitBox.setSelectedItem(currencyFromBox.getSelectedItem());
        panel.add(new JLabel(Lang.get("tab_cur") + ":")); panel.add(unitBox);
        JTextField rateField = new JTextField();
        panel.add(new JLabel(Lang.get("act_edit_rate") + ":")); panel.add(rateField);
        ActionListener updateRateField = e -> {
            String selected = (String) unitBox.getSelectedItem();
            if (selected != null) rateField.setText(String.valueOf(baseC.getRate(selected)));
        };
        unitBox.addActionListener(updateRateField);
        updateRateField.actionPerformed(null);
        int result = JOptionPane.showConfirmDialog(this, panel, Lang.get("act_edit_rate"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String unit = (String) unitBox.getSelectedItem();
                if (unit != null && !rateField.getText().isEmpty()) {
                    baseC.setRate(unit, Double.parseDouble(rateField.getText().replace(',', '.')));
                    saveConfig();
                    JOptionPane.showMessageDialog(this, Lang.get("msg_upd"));
                    if (currencyFromBox != null) currencyFromBox.setSelectedIndex(currencyFromBox.getSelectedIndex());
                }
            } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, Lang.get("err_num")); }
        }
    }

    private void showHistoryWindow() {
        JTextArea area = new JTextArea(HistoryLogger.getLastTenLines());
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, Lang.get("win_hist"), JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConverterApp().setVisible(true));
    }
}