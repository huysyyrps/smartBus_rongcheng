package main.wheel.widget.adapters;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractWheelAdapter
  implements WheelViewAdapter
{
  private List<DataSetObserver> datasetObservers;

  public View getEmptyItem(View paramView, ViewGroup paramViewGroup)
  {
    return null;
  }

  protected void notifyDataChangedEvent()
  {
    Iterator localIterator;
    if (this.datasetObservers != null){
      localIterator = this.datasetObservers.iterator();
      while(localIterator.hasNext()){
    	  ((DataSetObserver)localIterator.next()).onChanged();
      }
    }
 
  }

  protected void notifyDataInvalidatedEvent()
  {
    Iterator localIterator;
    if (this.datasetObservers != null){
      localIterator = this.datasetObservers.iterator();
      while(localIterator.hasNext()){
    	  ((DataSetObserver)localIterator.next()).onInvalidated();
      }
    }
  }

  public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    if (this.datasetObservers == null)
      this.datasetObservers = new LinkedList();
    this.datasetObservers.add(paramDataSetObserver);
  }

  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    if (this.datasetObservers == null)
      return;
    this.datasetObservers.remove(paramDataSetObserver);
  }
}
