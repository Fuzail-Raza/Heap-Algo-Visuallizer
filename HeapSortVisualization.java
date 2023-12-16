import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;

public class HeapSortVisualization {
    private int[] array;
    private int currentIndex;
    private JLabel swapHeading;
    private JLabel swapFirst;
    private JLabel swapSecond;
    private HeapPanel heapPanel;
    private boolean isHeapBuilded;
    private boolean isSorted;
    private int swapIndex1 = -1; // Initialize to -1 when no swapping is happening
    private int swapIndex2 = -1; // Initialize to -1 when no swapping is happening
    private JFrame mainFrame;

    public HeapSortVisualization(int[] array) {
        this.array = Arrays.copyOf(array, array.length);
        this.currentIndex = array.length - 1;

        Font labelFont = new Font("Arial", Font.BOLD, 18);

        mainFrame = new JFrame("Heap Sort Visualization");
        mainFrame.setLayout(null);

        heapPanel = new HeapPanel();
        swapHeading = new JLabel("To Swap");
        swapHeading.setFont(labelFont);
        swapHeading.setBounds(625, 110, 95, 35);
        swapHeading.setHorizontalAlignment(SwingConstants.CENTER);
        swapHeading.setBorder(new LineBorder(Color.gray, 2, true));
        heapPanel.add(swapHeading);

        Font f1 = new Font("Arial", Font.BOLD, 15);
        swapFirst = new JLabel("--");
        swapFirst.setBounds(575, 145, 95, 35);
        swapFirst.setFont(f1);
        swapFirst.setBorder(new LineBorder(Color.gray, 2, true));
        swapFirst.setHorizontalAlignment(SwingConstants.CENTER);
        heapPanel.add(swapFirst);

        swapSecond = new JLabel("--");
        swapSecond.setBounds(668, 145, 100, 35);
        swapSecond.setFont(f1);
        swapSecond.setBorder(new LineBorder(Color.gray, 2, true));
        swapSecond.setHorizontalAlignment(SwingConstants.CENTER);
        heapPanel.setBounds(2, 2, 700, 500);
        heapPanel.add(swapSecond);

        JButton sortButton = new JButton("Sort");
        JButton buildHeapButton = new JButton("Built Heap");

        buildHeapButton.addActionListener(e -> {
            if (!isHeapBuilded) {
                buildMaxHeap();
                isHeapBuilded = true;
            }
            JOptionPane.showMessageDialog(null, "Heap Already Built");
            heapPanel.repaint();
        });
        sortButton.addActionListener(e -> {
            if (!isHeapBuilded) {
                buildMaxHeap();
            }
            heapSort();
            heapPanel.repaint();
        });

        heapPanel.add(sortButton);
        heapPanel.add(buildHeapButton);
        mainFrame.add(heapPanel);
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void heapSort() {
        for (int i = currentIndex; i > 0; i--) {

            swap(0, i);
            currentIndex--;
            maxHeapify(0);
            heapPanel.repaint(); // Repaint after swapping
//            try {
//                Thread.sleep(1000); // Add a delay to visualize the swapping
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            swapIndex1 = -1; // Reset swap indices
            swapIndex2 = -1;
            heapPanel.repaint(); // Repaint to revert the color change
        }
        JOptionPane.showMessageDialog(null, "Sorted");
    }

    private void buildMaxHeap() {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            swapIndex1 = i;
            maxHeapify(i);
            heapPanel.repaint(); // Repaint to show the swapping color
//            try {
//                Thread.sleep(1000); // Add a delay to visualize the swapping
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            swapIndex1 = -1; // Reset swap index
            heapPanel.repaint(); // Repaint to revert the color change
        }
        JOptionPane.showMessageDialog(null, "Heap Built");
    }

    private void maxHeapify(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left <= currentIndex && array[left] > array[largest]) {
            largest = left;
        }

        if (right <= currentIndex && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            swapFirst.setText(String.valueOf(array[largest]));
            swapSecond.setText(String.valueOf(array[i]));
            swapIndex1 = largest;
            swapIndex2 = i;
            heapPanel.repaint(); // Repaint to show the swapping color
            swap(i, largest);
            JOptionPane.showMessageDialog(null, "Swap : " + array[largest] + " with " + array[i]);

            maxHeapify(largest);
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    class HeapPanel extends JPanel {
        private static final int CIRCLE_RADIUS = 30;
        private static final int HORIZONTAL_GAP = 50;
        private static final int VERTICAL_GAP = 80;

        class NodeInfo {
            int x;
            int y;

            public NodeInfo(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        protected void paintComponent(Graphics g) {
            Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

            TitledBorder titledBorder = BorderFactory.createTitledBorder(etchedBorder, "HEAP VISUALIZATION");
            titledBorder.setTitleColor(Color.BLACK);
            Font labelFont = new Font("Arial", Font.BOLD, 18);
            titledBorder.setTitleJustification(TitledBorder.CENTER);
            titledBorder.setTitleFont(labelFont);

            setBorder(titledBorder);
            super.paintComponent(g);

            NodeInfo[] nodeCoordinates = {
                    new NodeInfo(350, 100),
                    new NodeInfo((int) (350 / 1.5), 160),
                    new NodeInfo((int) (350 / 1.5) * 2, 160),
                    new NodeInfo((int) (233 / 1.5), 220),
                    new NodeInfo((int) (233 / 1.5) * 2, 220),
                    new NodeInfo(395, 220),
                    new NodeInfo(530, 220),
                    new NodeInfo((int) (155 / 1.5), 280),
                    new NodeInfo((int) (155 / 1.5) * 2, 280)
            };

            int rootNodeIndex = 0;

            // Draw the tree using a loop
            for (int currentIndex = 0; currentIndex < array.length; currentIndex++) {
                NodeInfo currentNode = nodeCoordinates[currentIndex];
                if (currentIndex == swapIndex1 || currentIndex == swapIndex2) {
                    // Change the color to red for nodes being swapped
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.drawOval(currentNode.x, currentNode.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(array[currentIndex]), currentNode.x + CIRCLE_RADIUS / 2 - 5, currentNode.y + CIRCLE_RADIUS / 2 + 5);

                // Draw lines between parent and child nodes
                int parentX = currentNode.x + CIRCLE_RADIUS / 2;
                int parentY = currentNode.y + CIRCLE_RADIUS;

                int leftChildIndex = 2 * currentIndex + 1;
                int rightChildIndex = 2 * currentIndex + 2;

                drawLine(g, parentX, parentY, nodeCoordinates, leftChildIndex);
                drawLine(g, parentX, parentY, nodeCoordinates, rightChildIndex);
            }
        }

        private void drawLine(Graphics g, int parentX, int parentY, NodeInfo[] nodeCoordinates, int childIndex) {
            if (childIndex < array.length) {
                NodeInfo childNode = nodeCoordinates[childIndex];
                g.drawLine(parentX, parentY, childNode.x + CIRCLE_RADIUS / 2, childNode.y);
            }
        }
    }

    public void run() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        int[] arrayToSort = {4, 10, 3, 5, 1, 2, 12, 9, 6};
        SwingUtilities.invokeLater(() -> {
            HeapSortVisualization heapSortVisualization = new HeapSortVisualization(arrayToSort);
            heapSortVisualization.run();
        });
    }
}
