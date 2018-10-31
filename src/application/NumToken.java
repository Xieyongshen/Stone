package application;

public class NumToken extends Token{
    private double value;
    protected NumToken(int line, double v) {
        super(line);
        value = v;
    }
    public boolean isNumber() { return true; }
    public int getType() { return 1; }
    public String getText() {
        if(value % 1.0 == 0){
            return String.valueOf((int)value);
        }

        return Double.toString(value);
    }
    public double getNumber() { return value; }
}
