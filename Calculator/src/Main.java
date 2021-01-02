import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main {
    public static class mainDisplay extends JFrame{
        Font mainFont = new Font("Arial", Font.BOLD, 12);
        mainDisplay(){
            //Setting variables for visual dimensions.
            int frameHeight = 500;
            int frameWidth = 400;
            JLabel selectedMeasurement = new JLabel("Millimeter/s");
            SetFont();

            //Initialise items.
            JMenuBar menuBar = new JMenuBar();
            JMenu prisms = new JMenu("Prisms");
            JMenu pyramids = new JMenu("Pyramids");
            JMenuItem cube = new JMenuItem("Cube"),
                    rectPrism = new JMenuItem("Rectangular Prism"),
                    triPrism = new JMenuItem("Triangular Prism"),
                    cone = new JMenuItem("Cone"),
                    sphere = new JMenuItem("Sphere"),
                    torus = new JMenuItem("Torus"),
                    cylinder = new JMenuItem("Cylinder"),
                    triPyramid = new JMenuItem("Triangular Pyramid"),
                    squarePyramid = new JMenuItem("Rectangular Pyramid");
            JMenuItem[] prismItems = {rectPrism, triPrism};
            JMenuItem[] pyramidItems = {triPyramid, squarePyramid};
            JMenuItem[] menuItems = {prisms, pyramids, cube, cone, sphere, torus, cylinder};

            //Label for the users Selected Shape.
            JLabel selectedShape = new JLabel("Select a shape");

            //Creating text fields for user inputs.
            JTextField lengthInput = new JTextField(),
                    heightInput = new JTextField(),
                    depthInput = new JTextField();
            JTextField[] inputs = {lengthInput, heightInput, depthInput};

            //Creating labels to show which text field is where.
            JLabel lengthLabel = new JLabel("Input Length: "),
                    heightLabel = new JLabel("Input Height: "),
                    depthLabel = new JLabel("Input Depth: "),
                    shapeLabel = new JLabel("Shape Name: ");
            JLabel[] labels = {lengthLabel, heightLabel, depthLabel, shapeLabel};

            //Creating the panels for each input to be contained in.
            JPanel lengthPanel = new JPanel(),
                    heightPanel = new JPanel(),
                    depthPanel = new JPanel(),
                    shapePanel = new JPanel();
            JPanel[] inputPanels = {lengthPanel, heightPanel, depthPanel, shapePanel};

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
                    switch (Objects.requireNonNull(selectedMeasure)) {
                        case "mm" -> selectedMeasurement.setText("Millimeter/s");
                        case "cm" -> selectedMeasurement.setText("Centimeter/s");
                        case "m" -> selectedMeasurement.setText("Meter/s");
                        case "km" -> selectedMeasurement.setText("Kilometer/s");
                        default -> selectedMeasurement.setText("Invalid Measurement");
                    }
                }
            });

            //Create and set display of items.
            Dimension menuItemStyle = new Dimension(50, 50);
            cube.setSize(menuItemStyle);
            rectPrism.setSize(menuItemStyle);

            int y = 100;
            int count = 0;
            Color inputPanelBg = new Color(230,230,230);
            for (JPanel inputPanel : inputPanels) {
                inputPanel.setBounds(15, y, 250, 25);
                inputPanel.setBackground(inputPanelBg);
                inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.X_AXIS));
                try {
                    inputPanel.add(labels[count]);
                    inputPanel.add(inputs[count]);
                }catch (Exception exception){
                    inputPanel.add(labels[count]);
                    inputPanel.add(selectedShape);
                }
                y += 50;
                count += 1;
            }
            volume.setBounds(10,400,200,25);
            calculate.setBounds(225, 400, 150,25);
            measurements.setBounds(300, 100, 50,25);
            selectedMeasurement.setBounds(300, 125, 75, 25);

            //Button and menu item actions
            ActionListener shape = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String sourceText = e.getActionCommand();
                    selectedShape.setText(sourceText);
                    switch (sourceText) {
                        case "Cube" -> {
                            inputPanels[0].setVisible(true);
                            lengthLabel.setText("Input Length: ");
                            inputPanels[1].setVisible(false);
                            inputPanels[2].setVisible(false);
                        }
                        case "Sphere" -> {
                            inputPanels[0].setVisible(true);
                            lengthLabel.setText("Input Radius: ");
                            inputPanels[1].setVisible(false);
                            inputPanels[2].setVisible(false);
                        }
                        case "Cone" -> {
                            inputPanels[0].setVisible(true);
                            lengthLabel.setText("Input Radius: ");
                            inputPanels[1].setVisible(true);
                            inputPanels[2].setVisible(false);

                        }
                        case "Torus" -> {
                            inputPanels[0].setVisible(true);
                            lengthLabel.setText("Input Minor Radius: ");
                            inputPanels[1].setVisible(true);
                            heightLabel.setText("Input Major Radius: ");
                            inputPanels[2].setVisible(false);
                        }
                        case "Cylinder" -> {
                            inputPanels[0].setVisible(true);
                            lengthLabel.setText("Input Radius: ");
                            inputPanels[2].setVisible(false);
                            inputPanels[1].setVisible(true);
                        }
                        case "Triangular Pyramid", "Rectangular Pyramid", "Rectangular Prism", "Triangular Prism" -> {
                            lengthLabel.setText("Input Length: ");
                            inputPanels[0].setVisible(true);
                            inputPanels[1].setVisible(true);
                            inputPanels[2].setVisible(true);
                        }

                        default -> {
                            inputPanels[0].setVisible(true);
                            inputPanels[1].setVisible(true);
                            inputPanels[2].setVisible(true);
                            lengthLabel.setText("Input Length: ");
                        }
                    }

                }
            };
            for (JMenuItem mainItem: menuItems){
                if (mainItem.getText().equals("Prisms")){
                    for (JMenuItem groupedItem: prismItems){
                        groupedItem.addActionListener(shape);
                    }
                }else if(mainItem.getText().equals("Pyramids")){
                    for(JMenuItem groupedItem : pyramidItems){
                        groupedItem.addActionListener(shape);
                    }
                }else{
                    mainItem.addActionListener(shape);
                }
            }
            calculate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String result = CalcVolume(selectedShape, lengthInput, heightInput, depthInput);
                    if(result.equals("Min Radius < Maj Radius") || result.equals("Please use a number")){
                        volume.setText(result);
                    } else{
                        volume.setText(String.format("%s %s\u00B3", result, measurements.getSelectedItem()));
                    }

                }
            });

            //Styling
            Border menuBorder = BorderFactory.createMatteBorder(0,1,0,1, Color.GRAY),
                    resultBorder = BorderFactory.createRaisedBevelBorder();
            volume.setBorder(resultBorder);

            //Setting Application Icon and Taskbar Icon
            ImageIcon iconImage = new ImageIcon("icon.png");
            setIconImage(iconImage.getImage());

            /*Adding everything to be shown on the JFrame.*/
            //Adding items to frame and adjusting frame settings.
            for (JMenuItem item : prismItems){
                prisms.add(item);
            }
            for (JMenuItem item : pyramidItems){
                pyramids.add(item);
            }
            for (JMenuItem item : menuItems){
                item.setBorder(menuBorder);
                menuBar.add(item);
            }
            setJMenuBar(menuBar);

            //Adding the input panels
            for (JPanel panel: inputPanels){
                add(panel);
            }

            //Adding extras
            add(volume);
            add(calculate);
            add(measurements);
            add(selectedMeasurement);
            setTitle("Volume Calculator");
            setSize(frameWidth,frameHeight);
            setLayout(null);
            setVisible(true);

            //Set the "X" button to stop running the program.
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }

        private void SetFont() {
            UIManager.put("Button.font", mainFont);
            UIManager.put("ComboBox.font", mainFont);
            UIManager.put("MenuBar.font", mainFont);
            UIManager.put("MenuItem.font", mainFont);
            UIManager.put("Menu.font", mainFont);
            UIManager.put("Label.font", mainFont);
            UIManager.put("TextField.font", mainFont);
        }

        private String CalcVolume(JLabel shape, JTextField length, JTextField height, JTextField depth) {
            //Calculates the volume of the selected shape with the selected side length
            float lengthNum = 0,
                    heightNum = 0,
                    depthNum = 0;
            if(!length.getText().equals("")){
                lengthNum = Float.parseFloat(length.getText());
            }
            if(!height.getText().equals("")){
                heightNum = Float.parseFloat(height.getText());
            }
            if(!depth.getText().equals("")){
                depthNum = Float.parseFloat(depth.getText());
            }
            if (shape.getText().equals("Torus") && lengthNum > heightNum){
                return "Min Radius < Maj Radius";
            }

            try{
                return switch (shape.getText()) {
                    case "Cube" -> String.format("%.2f", Math.pow(lengthNum, 3));
                    case "Rectangular Prism" -> String.format("%.2f", lengthNum * heightNum * depthNum);
                    case "Sphere" -> String.format("%.2f", (4f/3f) * Math.PI * (Math.pow(lengthNum, 3)));
                    case "Cone" -> String.format("%.2f", Math.PI * (Math.pow(lengthNum, 2) * (heightNum/3)));
                    case "Triangular Prism" -> String.format("%.2f", (lengthNum * heightNum * depthNum)/2);
                    case "Torus" -> String.format("%.2f", (Math.PI * Math.pow(lengthNum, 2)) * ((2 * Math.PI * heightNum)));
                    case "Cylinder" -> String.format("%.2f", Math.PI * Math.pow(lengthNum, 2) * heightNum);
                    case "Triangular Pyramid" -> String.format("%.2f", (((lengthNum * depthNum) / 2) * heightNum) / 3);
                    case "Rectangular Pyramid" -> String.format("%.2f", ((lengthNum * depthNum) * heightNum) / 3);
                    case "Select a shape" -> "Please select a shape";
                    default -> "Please input a length";
                };
            }
            catch(Exception e){
                //This catches if a user inputs anything that isn't a number.
                return "Please use a number";
            }

        }


    }
    public static void main(String[] args) { new mainDisplay(); }
}
