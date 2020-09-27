package src;

import src.graphics.drawers.arc_graphics;
import src.graphics.drawers.ellipse_graphics;
import src.graphics.drawers.line_graphics;
import src.utils.coordinate_2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

public class canvas extends JPanel
{
    private int mode = 0;
    private boolean fill = false;
    private boolean angle = false;

    private int zoom_multiplayer = 4;

    private coordinate_2d line_begin = null;
    private coordinate_2d line_end = null;

    private coordinate_2d ellipse_center = null;
    private coordinate_2d ellipse_helper_0 = null;
    private coordinate_2d ellipse_helper_1 = null;

    public canvas()
    {
        print_state();
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_TAB)
                {
                    ++mode;
                    if (mode > 2)
                        mode = 0;
                    print_state();
                    repaint();
                }
                else
                if (e.getKeyCode() == KeyEvent.VK_1)
                {
                    fill = !fill;
                    print_state();
                    repaint();
                }
                else
                if (e.getKeyCode() == KeyEvent.VK_2)
                {
                    angle = !angle;
                    print_state();
                }
            }
        });

        addMouseWheelListener(e ->
        {
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
            {
                if (e.getWheelRotation() == -1)
                {
                    zoom_multiplayer *= 2;
                    if (zoom_multiplayer > 64)
                        zoom_multiplayer = 64;
                    repaint();
                }
                else
                if (e.getWheelRotation() == 1)
                {
                    zoom_multiplayer /= 2;
                    if (zoom_multiplayer < 1)
                        zoom_multiplayer = 1;
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if (!angle && e.isControlDown() && e.getButton() == 1)
                {
                    line_begin = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                    repaint();
                }

                if (!angle && e.isAltDown() && e.getButton() == 1)
                {
                    line_end = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                    repaint();
                }
                else
                if (e.isShiftDown() && e.getButton() == 1)
                {
                    ellipse_center = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                }
                else
                if (angle && e.isControlDown() && e.getButton() == 1)
                {
                    ellipse_helper_0 = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                    repaint();
                }
                else
                if (angle && e.isAltDown() && e.getButton() == 1)
                {
                    ellipse_helper_1 = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                if (!angle && e.isControlDown())
                {
                    line_begin = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                }

                if (!angle && e.isAltDown())
                {
                    line_end = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                }

                if (angle && e.isControlDown())
                {
                    ellipse_helper_0 = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                }

                if (angle && e.isAltDown())
                {
                    ellipse_helper_1 = new coordinate_2d(e.getX()/zoom_multiplayer, e.getY()/zoom_multiplayer);
                }

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
    }

    private void clear_screen(Graphics2D graphics)
    {
        graphics.setColor(new Color(255,255,255));
        graphics.fillRect(0,0,getWidth(),getHeight());
    }

    public void paint(Graphics graphics)
    {
        Graphics2D graphics_2d = (Graphics2D)graphics;
        clear_screen(graphics_2d);

        line_graphics lg = new line_graphics();
        ellipse_graphics el = new ellipse_graphics(true);
        arc_graphics ag = new arc_graphics(true);

        switch (mode)
        {
            case 0:
                if (line_begin != null && line_end != null)
                    lg.draw_line_WU(graphics_2d, line_begin, line_end, new Color(139,0,255), zoom_multiplayer);
                break;

            case 1:
                if (line_begin != null && line_end != null && ellipse_center != null)
                {
                    if (!fill)
                    {
                        el.draw_ellipse_WUlike(graphics_2d, ellipse_center, ellipse_center.distance_to(line_begin),
                                ellipse_center.distance_to(line_end), new Color(139,0,255), zoom_multiplayer);
                    }
                    else
                        el.fill_ellipse(graphics_2d, ellipse_center, ellipse_center.distance_to(line_begin),
                                ellipse_center.distance_to(line_end), new Color(139,0,255), zoom_multiplayer);
                }
                break;

            case 2:
                if (line_begin != null && line_end != null && ellipse_center != null && ellipse_helper_0 != null && ellipse_helper_1 != null)
                {
                    int first_angle = get_degrees(ellipse_center, ellipse_helper_0);
                    int second_angle = get_degrees(ellipse_center, ellipse_helper_1) - first_angle;

                    if (!fill)
                        ag.draw_arc(graphics_2d, ellipse_center, ellipse_center.distance_to(line_begin),
                                    ellipse_center.distance_to(line_end), first_angle, second_angle, new Color(139,0,255), zoom_multiplayer);
                    else
                    {
                        ag = new arc_graphics(false);
                        ag.fill_arc(graphics_2d, ellipse_center, ellipse_center.distance_to(line_begin),
                                ellipse_center.distance_to(line_end), first_angle, second_angle, new Color(139,0,255), zoom_multiplayer);
                    }
                }
        }
    }

    private int get_degrees(coordinate_2d first, coordinate_2d second)
    {
        double angle = Math.toDegrees(Math.atan2(second.get_y()-first.get_y(), second.get_x()-first.get_x()));

        angle = -angle;

        if (angle < 0)
            angle += 360;

        return (int)Math.round(angle);
    }

    private void print_state()
    {
        System.out.print("[MODE: ");
        switch (mode)
        {
            case 0:
                System.out.print("line");
                break;

            case 1:
                System.out.print("ellipse; FILLED: ");
                if (fill)
                    System.out.print("true");
                else
                    System.out.print("false");
                break;

            case 2:
                System.out.print("arc; INPUT: ");
                if (angle)
                    System.out.print("angles; ");
                else
                    System.out.print("coordinates; ");
                System.out.print("FILLED: ");
                if (fill)
                    System.out.print("true");
                else
                    System.out.print("false");
        }

        System.out.println("]");
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}
