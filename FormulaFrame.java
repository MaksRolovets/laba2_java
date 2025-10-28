
    import javax.swing.*;
    import java.awt.*;


    public class FormulaFrame extends JFrame {
        private static final int WIDTH = 500;
        private static final int HEIGHT = 400;

        private JTextField textFieldX;
        private JTextField textFieldY;
        private JTextField textFieldZ;
        private JTextField textFieldResult;

        private ButtonGroup radioButtons = new ButtonGroup();
        private Box hboxFormulaType = Box.createHorizontalBox();
        private int formulaId = 1;

        private Double lastResult_1 = null;
        private Double lastResult_2 = null;
        private Double sum = 0.0;

        public FormulaFrame() {
            super("Вычисление формул (Вариант 8)");

            setSize(WIDTH, HEIGHT);
            Toolkit kit = Toolkit.getDefaultToolkit();
            setLocation((kit.getScreenSize().width - WIDTH) / 2,
                    (kit.getScreenSize().height - HEIGHT) / 2);

            // Формулы
            addRadioButton("Формула 1", 1);
            addRadioButton("Формула 2", 2);
            radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);
            hboxFormulaType.setBorder(BorderFactory.createTitledBorder("Выбор формулы"));

            // Поля ввода
            Box hboxVars = Box.createHorizontalBox();
            textFieldX = new JTextField("0", 8);
            textFieldY = new JTextField("0", 8);
            textFieldZ = new JTextField("0", 8);
            hboxVars.add(new JLabel("X:")); hboxVars.add(textFieldX);
            hboxVars.add(Box.createHorizontalStrut(15));
            hboxVars.add(new JLabel("Y:")); hboxVars.add(textFieldY);
            hboxVars.add(Box.createHorizontalStrut(15));
            hboxVars.add(new JLabel("Z:")); hboxVars.add(textFieldZ);
            hboxVars.setBorder(BorderFactory.createTitledBorder("Переменные"));

            // Поле результата
            Box hboxRes = Box.createHorizontalBox();
            textFieldResult = new JTextField("0", 15);
            textFieldResult.setEditable(false);
            hboxRes.add(new JLabel("Результат:"));
            hboxRes.add(Box.createHorizontalStrut(10));
            hboxRes.add(textFieldResult);

            // Кнопки
            JButton buttonCalc = new JButton("Вычислить");
            buttonCalc.addActionListener(e -> calculate());

            JButton buttonReset = new JButton("Очистить");
            buttonReset.addActionListener(e -> {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            });

            JButton buttonMPlus = new JButton("M+");
            buttonMPlus.addActionListener(e -> {
                try {
                    if (formulaId == 1){
                        if (lastResult_1 == null){
                            JOptionPane.showMessageDialog(this, "Сначала выполните вычисление", "Нет результата", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        sum += lastResult_1;
                        textFieldResult.setText(sum.toString());
                        lastResult_1 = sum;
                    }if (formulaId == 2){
                        if (lastResult_2 == null){
                            JOptionPane.showMessageDialog(this, "Сначала выполните вычисление", "Нет результата", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        sum += lastResult_2;
                        textFieldResult.setText(sum.toString());
                        lastResult_2 = sum;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Ошибка при добавлении к сумме");
                }
            });

            JButton buttonMC = new JButton("MC");
            buttonMC.addActionListener(e -> {
                sum = 0.0;
                lastResult_1 = null;
                lastResult_2 = null;
                textFieldResult.setText("0");
            });

            Box hboxButtons = Box.createHorizontalBox();
            hboxButtons.add(buttonCalc);
            hboxButtons.add(Box.createHorizontalStrut(15));
            hboxButtons.add(buttonReset);
            hboxButtons.add(Box.createHorizontalStrut(15));
            hboxButtons.add(buttonMPlus);
            hboxButtons.add(Box.createHorizontalStrut(15));
            hboxButtons.add(buttonMC);

            // Общая компоновка
            Box contentBox = Box.createVerticalBox();
            contentBox.add(hboxFormulaType);
            contentBox.add(hboxVars);
            contentBox.add(hboxRes);
            contentBox.add(hboxButtons);

            getContentPane().add(contentBox, BorderLayout.CENTER);
        }

        private void addRadioButton(String name, final int formulaId) {
            JRadioButton button = new JRadioButton(name);
            button.addActionListener(e -> {
                if (formulaId == 1){lastResult_1 = null;}
                else{lastResult_2 = null;}
                this.formulaId = formulaId;
                textFieldResult.setText("0");

            });
            radioButtons.add(button);
            hboxFormulaType.add(button);
        }

        private void calculate() {
            try {
                double x = Double.parseDouble(textFieldX.getText());
                double y = Double.parseDouble(textFieldY.getText());
                double z = Double.parseDouble(textFieldZ.getText());
                double result;

                if (formulaId == 1) {
                    result = formula1(x, y, z);
                    lastResult_1 = result;
                }
                else{
                    result = formula2(x, y, z);
                    lastResult_2 = result;
                }
                textFieldResult.setText(String.format("%.5f", result));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка вычислений: " + ex.getMessage(),
                        "Ошибка", JOptionPane.WARNING_MESSAGE);
            }
        }

        // Формула №1
        private double formula1(double x, double y, double z) {
            return Math.pow(Math.cos(Math.PI*Math.pow(x,3)) + Math.pow(Math.log(1+y), 2), 0.25)
                    * (Math.pow(Math.pow(Math.exp(x), z), 2)+Math.sqrt(1/x)+Math.cos(Math.pow(Math.E, y)));
        }

        // Формула №2
        private double formula2(double x, double y, double z) {
            return Math.pow(x,x) / (Math.sqrt((Math.pow(y, 3) + 1)));
        }
    }


