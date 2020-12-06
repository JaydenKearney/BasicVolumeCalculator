import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main {
    public static class mainDisplay extends JFrame{
        mainDisplay(){
            //Setting variables for visual dimensions.
            int frameHeight = 500;
            int frameWidth = 400;
            JLabel selectedMeasurement = new JLabel("Millimeter/s");

            //Initialise items.
            JMenuBar menuBar = new JMenuBar();
            JMenu prisms = new JMenu("Prisms");
            JMenuItem cube = new JMenuItem("Cube"),
                    rectPrism = new JMenuItem("Rectangular Prism"),
                    triPrism = new JMenuItem("Triangular Prism"),
                    cone = new JMenuItem("Cone"),
                    sphere = new JMenuItem("Sphere"),
                    tesseract = new JMenuItem("Tesseract");
            JMenuItem[] prismItems = {rectPrism, triPrism};
            JMenuItem[] menuItems = {cube, prisms, cone, sphere, tesseract};
            JLabel SelectedShape = new JLabel("Select a shape");
            JTextField lengthInput = new JTextField(),
                    heightInput = new JTextField(),
                    depthInput = new JTextField(),
                    timeInput = new JTextField();
            JTextField[] inputs = {lengthInput, heightInput, depthInput, timeInput};
            //4D calculations need some work to be able to implement

            JLabel lengthLabel = new JLabel("Input Length: "),
                    heightLabel = new JLabel("Input Height: "),
                    depthLabel = new JLabel("Input Depth: "),
                    timeLabel = new JLabel("Input Time: "),
                    shapeLabel = new JLabel("Shape Name"),
                    volumeLabel = new JLabel("Volume: ");
            JLabel[] labels = {shapeLabel, lengthLabel, heightLabel, depthLabel, timeLabel};

            JLabel volume = new JLabel("Result");
            JButton calculate = new JButton("Calculate");
            String[] measures = {"mm", "cm", "m", "km"};
            JComboBox measurements = new JComboBox(measures);
            measurements.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Finds which item has been selected and changes it to be selected.
                    JComboBox cb = (JComboBox)e.getSource();
                    String selectedMeasure = (String)cb.getSelectedItem();
                    switch (selectedMeasure) {
                        case "mm" -> selectedMeasurement.setText("Millimeter/s");
                        case "cm" -> selectedMeasurement.setText("Centimeter/s");
                        case "m" -> selectedMeasurement.setText("Meter/s");
                        case "km" -> selectedMeasurement.setText("Kilometer/s");
                    }
                }
            });

            //Create and set display of items.
            Dimension menuItemStyle = new Dimension(50, 50);
            cube.setSize(menuItemStyle);
            rectPrism.setSize(menuItemStyle);
            int y = 100;
            for (JLabel label : labels) {
                label.setBounds(15, y, 100, 25);
                y += 50;
            }
            y = 150;
            for (JTextField input : inputs){
                input.setBounds(125, y, 150, 25);
                y += 50;
            }

            //Time does not need to display automatically.
            timeInput.setVisible(false);
            timeLabel.setVisible(false);

            SelectedShape.setBounds(125,100,150,25);
            volumeLabel.setBounds(25,400,150,25);
            volume.setBounds(75,400,150,25);
            calculate.setBounds(225, 400, 150,25);
            measurements.setBounds(300, 100, 50,25);
            selectedMeasurement.setBounds(300, 125, 75, 25);

            //Button and menu item actions
            ActionListener shape = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String sourceText = e.getActionCommand();
                    SelectedShape.setText(sourceText);
                    switch (sourceText) {
                        case "Cube" -> {
                            heightLabel.setVisible(false);
                            heightInput.setVisible(false);
                            depthLabel.setVisible(false);
                            depthInput.setVisible(false);
                            timeLabel.setVisible(false);
                            timeInput.setVisible(false);
                        }
                        case "Tesseract" -> {
                            heightLabel.setVisible(true);
                            heightInput.setVisible(true);
                            depthLabel.setVisible(true);
                            depthInput.setVisible(true);
                            timeLabel.setVisible(true);
                            timeInput.setVisible(true);

                        }
                        default -> {
                            heightLabel.setVisible(true);
                            heightInput.setVisible(true);
                            depthLabel.setVisible(true);
                            depthInput.setVisible(true);
                            timeLabel.setVisible(false);
                            timeInput.setVisible(false);
                        }
                    }

                }
            };
            cube.addActionListener(shape);
            rectPrism.addActionListener(shape);
            tesseract.addActionListener(shape);
            calculate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    volume.setText(CalcVolume(SelectedShape, lengthInput, heightInput, depthInput, timeInput));
                }
            });

            //Adding items to frame and adjusting frame settings.
            for (JMenuItem item : prismItems){
                prisms.add(item);
            }
            for (JMenuItem item : menuItems){
                menuBar.add(item);
            }
            /*menuBar.add(cube);
            menuBar.add(rectPrism);
            menuBar.add(tesseract);*/
            setJMenuBar(menuBar);
            add(lengthLabel);
            add(depthLabel);
            add(heightLabel);
            add(volumeLabel);
            add(timeLabel);
            add(shapeLabel);

            add(SelectedShape);
            add(lengthInput);
            add(depthInput);
            add(heightInput);
            add(timeInput);

            add(volume);
            add(calculate);
            add(measurements);
            add(selectedMeasurement);
            setTitle("Volume Calculator");
            setSize(frameWidth,frameHeight);
            setLayout(null);
            setVisible(true);

        }

        private String CalcVolume(JLabel shape, JTextField length, JTextField height, JTextField depth, JTextField time) {
            //Calculates the volume of the selected shape with the selected side length
            int lengthNum = Integer.parseInt(length.getText());
            int heightNum = Integer.parseInt(height.getText());
            int depthNum = Integer.parseInt(depth.getText());
            int timeNum = Integer.parseInt(time.getText());

            try{
                return switch (shape.getText()) {
                    case "Cube" -> String.valueOf(Math.pow(Double.parseDouble(length.getText()), 3));
                    case "Rectangular Prism" -> String.valueOf((float)lengthNum * heightNum * depthNum);
                    case "Tesseract" -> String.valueOf(Math.pow(Double.parseDouble(length.getText()), 4));
                    case "Select a shape" -> "Please select a shape";
                    default -> "Please input a length";
                };
            }
            catch(Exception e){
                System.out.print(e.getMessage());
                return "Please use a number";
            }

        }


    }
    public static void main(String[] args) { new mainDisplay(); }
}
