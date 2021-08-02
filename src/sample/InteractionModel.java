package sample;

import java.util.ArrayList;

public class InteractionModel {
    XShape selectedShape;
    ArrayList selectionSet;
    ArrayList<SketchModelListener> subscribers;

    public InteractionModel(){
        subscribers = new ArrayList<>();
        selectedShape = null;
    }

    public void setSelection(ArrayList set){
        selectionSet = set;
        notifySubscribers();
    }

    public void clearSelection(){
        selectionSet.clear();
        notifySubscribers();
    }
    private void notifySubscribers(){
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public void addSubscriber(SketchModelListener aSub) {
        subscribers.add(aSub);
    }

    public void unSelect(){
        selectedShape = null;
        notifySubscribers();
    }
}
