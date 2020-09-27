package src.graphics.drawers;

import src.utils.coordinate_2d;

import java.awt.*;

public interface arc_drawer extends pixel_drawer, line_drawer
{
    default void draw_arc(Graphics2D graphics, coordinate_2d center, int width, int height, int begin_angle, int end_angle, Color color, int zoom_multiplayer, boolean is_alpha)
    {
        final int height_sqr = height * height;
        final int width_sqr = width * width;

        final float max_transparency = 255;
        int quarter = (int)Math.round(width_sqr/Math.sqrt(height_sqr+width_sqr));

        for (int x = 0; x <= quarter; ++x)
        {
            double y = height * Math.sqrt(1 - (double)x*x/width_sqr);
            double error = y - Math.floor(y);

            double transparency = Math.round(error * max_transparency);
            double alpha_0 = is_alpha ? transparency : 255;
            double alpha_1 = is_alpha ? max_transparency - transparency : 255;

            pixel_mirroring(graphics, center, x, (int)Math.floor(y), begin_angle, end_angle,
                    new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_0)), zoom_multiplayer);
            pixel_mirroring(graphics, center, x, (int)Math.floor(y)-1, begin_angle, end_angle,
                    new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_1)), zoom_multiplayer);
        }

        quarter = (int)Math.round(height_sqr/Math.sqrt(height_sqr+width_sqr));

        for (int y = 0; y <= quarter; ++y)
        {
            double x = width * Math.sqrt(1 - (double)y*y/height_sqr);
            double error = x - Math.floor(x);

            double transparency = (int)Math.round(error * max_transparency);
            double alpha_0 = is_alpha ? transparency : 255;
            double alpha_1 = is_alpha ? max_transparency - transparency : 255;

            pixel_mirroring(graphics, center, (int)Math.floor(x), y, begin_angle, end_angle,
                    new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_0)), zoom_multiplayer);
            pixel_mirroring(graphics, center, (int)Math.floor(x)-1, y, begin_angle, end_angle,
                    new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_1)), zoom_multiplayer);
        }
    }

    private void pixel_mirroring(Graphics2D graphics, coordinate_2d center, int x_offset, int y_offset, int begin_angle, int end_angle, Color color, int zoom_multiplayer)
    {
        double angle = Math.toDegrees(Math.atan2(y_offset, x_offset));

        if (begin_angle + angle <= 180 && end_angle+begin_angle + angle >= 180)
            draw_pixel(graphics, new coordinate_2d(center.get_x() - x_offset, center.get_y() - y_offset), color, zoom_multiplayer);

        if (angle >= begin_angle && angle <= end_angle+begin_angle)
            draw_pixel(graphics, new coordinate_2d(center.get_x() + x_offset, center.get_y() - y_offset), color, zoom_multiplayer);

        if (begin_angle + angle <= 360 && begin_angle+end_angle + angle >= 360)
            draw_pixel(graphics, new coordinate_2d(center.get_x() + x_offset, center.get_y() + y_offset), color, zoom_multiplayer);

        if (angle+180 >= begin_angle && angle + 180 <= begin_angle+end_angle)
            draw_pixel(graphics, new coordinate_2d(center.get_x() - x_offset, center.get_y() + y_offset), color, zoom_multiplayer);
    }
}
