package helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: Jasper
 * Class: Buttons made easy
 */

 public class MenuButton extends JButton implements MouseListener
{

    private int WIDTH      =   100;
    private int HEIGHT     =   100;

    private String activeImage;
    private String defaultImage;

    private Boolean active;

    /**
     * Constuctor, set default image and hide things we dont need
     */

    public MenuButton(String defaultImage, String activeImage, Dimension size)
    {
        super(new ImageIcon(ClassLoader.getSystemResource(defaultImage))); //create button with image

        this.activeImage = activeImage;
        this.defaultImage = defaultImage;
        this.active = false;

        if(size != null)
        {
            this.WIDTH = size.width;
            this.HEIGHT = size.height;
        }

        this.setBounds(0, 0, WIDTH, HEIGHT);

        this.setBorderPainted(false); //hide button border
        this.setFocusPainted(false); //remove focus
        this.setContentAreaFilled(false);

        if(activeImage != null)
        {
            this.addMouseListener(this);
        }
    }

    /**
     *
     * Menu button without size
     *
     * @param defaultImage
     * @param activeImage
     */

    public MenuButton(String defaultImage, String activeImage)
    {
        super(new ImageIcon(ClassLoader.getSystemResource(defaultImage))); //create button with image

        this.activeImage = activeImage;
        this.defaultImage = defaultImage;
        this.active = false;

        this.setBounds(0, 0, WIDTH, HEIGHT);

        this.setBorderPainted(false); //hide button border
        this.setFocusPainted(false); //remove focus
        this.setContentAreaFilled(false);

        if(activeImage != null)
        {
            this.addMouseListener(this);
        }
    }

    /**
     *
     * Menu button without size and hover
     *
     * @param defaultImage
     */

    public MenuButton(String defaultImage)
    {
        super(new ImageIcon(ClassLoader.getSystemResource(defaultImage))); //create button with image

        this.defaultImage = defaultImage;
        this.active = false;

        this.setBounds(0, 0, WIDTH, HEIGHT);

        this.setBorderPainted(false); //hide button border
        this.setFocusPainted(false); //remove focus
        this.setContentAreaFilled(false);
    }

    /**
     * Toggle active
     */

    public void toggleButton()
    {
        this.active = !this.active; //toggle activity of button
    }

    /**
     * activate button
     */

    public void activateButton()
    {
        this.setIcon(new ImageIcon(ClassLoader.getSystemResource(activeImage)));
        this.active = true; //toggle activity of button
    }

    /**
     * deactivate button
     */

    public void deactivateButton()
    {
        this.active = false; //toggle activity of button
        this.setIcon(new ImageIcon(ClassLoader.getSystemResource(defaultImage)));
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    /**
     * Hover effect for button
     */

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
            this.setIcon(new ImageIcon(ClassLoader.getSystemResource(activeImage))); //show hover image
    }

    /**
     * Change image back when mouse exit, only if button is inactive
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if(!this.active) //only if not active button otherwise the button would change back when still active
        {
            this.setIcon(new ImageIcon(ClassLoader.getSystemResource(defaultImage)));
        }
    }
}
