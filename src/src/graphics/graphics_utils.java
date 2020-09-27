package src.graphics;

import java.awt.*;

public final class graphics_utils
{
    private graphics_utils() {}

    public static Color get_color_with_alpha(Color color, double alpha)
    {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)Math.round(alpha*255));
    }
}
