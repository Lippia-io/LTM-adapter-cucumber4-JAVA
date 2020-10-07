package io.lippia.reporter.service;

class Label
implements Markup {
    private static final long serialVersionUID = 548763908072445261L;
    private String text = "";
    private Color color = Color.TRANSPARENT;

    Label() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public String getMarkup() {
        String textColor = this.color != Color.WHITE ? "white-text" : "black-text";
        String lhs = "<span class='badge " + textColor + " " + String.valueOf((Object)this.color).toLowerCase() + "'>";
        String rhs = "</span>";
        return lhs + this.text + rhs;
    }
}

