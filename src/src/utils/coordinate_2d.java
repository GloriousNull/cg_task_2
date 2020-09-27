package src.utils;

public class coordinate_2d
{
    private int x;
    private int y;

    public coordinate_2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public coordinate_2d(double x, double y)
    {
        this.x = (int)Math.round(x);
        this.y = (int)Math.round(y);
    }

    public int get_x()
    {
        return this.x;
    }

    public int get_y()
    {
        return this.y;
    }

    public void set_x(int x)
    {
        this.x = x;
    }

    public void set_y(int y)
    {
        this.y = y;
    }

    public int distance_to(coordinate_2d point)
    {
        return (int)Math.sqrt((this.x - point.get_x())*(this.x - point.get_x())+(this.y - point.get_y())*(this.y - point.get_y()));
    }
}
