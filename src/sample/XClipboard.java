package sample;

import java.util.ArrayList;

public class XClipboard {

    ArrayList<Groupable> items;

    public XClipboard() {
        items = new ArrayList<>();
    }

    public void copy(ArrayList<Groupable> gs) {
        items.clear();
        gs.forEach(g -> items.add(g.duplicate()));
    }

    public ArrayList<Groupable> get() {
        System.out.println("Before get, clipboard size is " + items.size());
        ArrayList<Groupable> copy = new ArrayList<Groupable>();
        items.forEach(item -> copy.add(item.duplicate()));
        return copy;
    }

    public String describeClipboard() {
        if (items.size() > 1) {
            return items.size() + " items";
        } else if (items.size() == 1) {
            return "1 item";
        } else {
            return "empty";
        }
    }
}
