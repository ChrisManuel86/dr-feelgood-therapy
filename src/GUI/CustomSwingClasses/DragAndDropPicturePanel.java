package gui.customSwingClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.lang.System.err;

/**
 * DragAndDropPicturePanel
 * Class that extends Picture Panel to display pictures in the Root Panel and allows them to be dropped in from outside
 * sources
 *
 * @author Christopher Manuel
 * @version 2023.08.17
 * <p>
 * Change Log:
 * - Refactored project, removing deprecated java calls
 */

public class DragAndDropPicturePanel extends PicturePanel implements DropTargetListener {
    private final static String DROP_TEXT = "Drop Image Here";
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean dragging = false;
    private BufferedImage image = null;
    // Offsets
    private int xOffset = 0;
    private int yOffset = 0;
    private int panelWidthOffset = 0;
    private int panelHeightOffset = 0;

    private Color bgColor = new Color(242, 242, 242, 255);
    private int panelWidth;
    private int panelHeight;

    public DragAndDropPicturePanel() {
        super();
        new DropTarget(this, this);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage value) {
        image = value;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();

        g.clearRect(0, 0, panelWidth - 1, panelHeight - 1);

        if (image != null) {
            imageCrop(image);

            // Draw image
            g.setColor(bgColor);
            g.fillRect(0, 0, panelWidth, panelHeight);
            g.drawImage(image, xOffset, yOffset, panelWidth - panelWidthOffset, panelHeight - panelHeightOffset, bgColor, null);

            // Reset Offsets
            panelWidthOffset = 0;
            panelHeightOffset = 0;
            xOffset = 0;
            yOffset = 0;
        } else {
            // Draw drop text
            int textWidth = g.getFontMetrics().stringWidth(DROP_TEXT);
            int textHeight = g.getFontMetrics().getHeight();
            g.setColor(Color.gray);
            g.drawString(DROP_TEXT, (panelWidth - textWidth) / 2, (panelHeight + textHeight / 2) / 2);
        }

        // Highlight the panel to show the user is dragging over it.
        if (dragging) {
            g.setColor(new Color(190, 211, 231, 150));
            g.fillRect(0, 0, panelWidth, panelHeight);
        }

        // Draw frame around panel
        g.drawRect(0, 0, panelWidth - 1, panelHeight - 1);
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        dragging = true;
        repaint();
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        dragging = false;
        repaint();
    }

    private void loadFile(File file) {
        try {
            BufferedImage tempImage = image;
            image = ImageIO.read(file);
            pcs.firePropertyChange("image", tempImage, ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace(err);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent dtde) {
        dragging = false;
        Transferable tr = dtde.getTransferable();
        DataFlavor[] flavors = tr.getTransferDataFlavors();

        for (DataFlavor flavor : flavors) {
            if (flavor.isFlavorJavaFileListType()) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                // And add the list of file names to our text area
                try {
                    List<File> list;
                    for (File files : list = Collections.unmodifiableList((List<File>) tr.getTransferData(flavor))) {
                        loadFile(list.get(0));
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace(err);
                }

                dtde.dropComplete(true);
                repaint();
                return;
            }
        }
        repaint();
    }

    void imageCrop(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double widthPercent = (double) panelWidth / imageWidth;
        double heightPercent = (double) panelHeight / imageHeight;
        double percentDifference;

        if (widthPercent > heightPercent) {
            percentDifference = widthPercent - heightPercent;

            widthPercent -= percentDifference;

            int minusPixels = panelWidth - (int) (image.getWidth() * widthPercent);

            xOffset = minusPixels / 2;
            panelWidthOffset = minusPixels;
        }
        if (widthPercent < heightPercent) {
            percentDifference = heightPercent - widthPercent;

            heightPercent -= percentDifference;

            int minusPixels = panelHeight - (int) (image.getHeight() * heightPercent);

            yOffset = minusPixels / 2;
            panelHeightOffset = minusPixels;
        }
    }

    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void setColor(Color color) {
        bgColor = color;
    }

    public void setDimensions(Dimension dimension) {
        panelWidth = (int) dimension.getWidth();
        panelHeight = (int) dimension.getHeight();
    }
}