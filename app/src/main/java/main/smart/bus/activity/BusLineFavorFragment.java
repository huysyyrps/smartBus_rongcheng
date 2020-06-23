package main.smart.bus.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;

import main.smart.rcgj.R;
import main.smart.bus.activity.BusLineDetailBusFragment.changedTab;
import main.smart.bus.bean.FavorLineBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.util.BusLineRefresh;
import main.smart.bus.util.BusManager;

public class BusLineFavorFragment extends Fragment implements BusLineRefresh {

	private changedTab listener;
	private Activity activity;
	private BusManager mBusMan;
	private int sxx;
	private TextView editLine;
	private EditText editName;
//	private EditText editLine;
	private EditText editLineInfo;
	private EditText editStartStation;
	private EditText editEndStation;
	private Button btnSave;
	private LineBean lineInfo;
	
	public BusLineFavorFragment() {
		// TODO Auto-generated constructor stub
		mBusMan = BusManager.getInstance();
	
	}

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
		lineInfo = mBusMan.getSelectedLine();
		sxx = lineInfo.getLineId();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		View view=null;
		try{
			view = inflater.inflate(R.layout.busline_favor_view, container, false);
			activity=(Activity) view.getContext();
			listener=(changedTab)activity;
			this.lineInfo = mBusMan.getSelectedLine();
			sxx = this.lineInfo.getLineId();
			
			editName=(EditText) view.findViewById(R.id.bus_favorite_name);
			editLine=(TextView) view.findViewById(R.id.bus_line_name);
			editLineInfo=(EditText) view.findViewById(R.id.bus_line_info);
			editStartStation=(EditText) view.findViewById(R.id.get_on_station);
			editEndStation=(EditText)view.findViewById(R.id.get_off_station);
            btnSave = (Button) view.findViewById(R.id.bus_favorite_confirm_btn);
			editName.setText(lineInfo.getLineName());
			editLine.setText(lineInfo.getLineName());
			String info;
			info = lineInfo.getBeginStation() + " - " + lineInfo.getEndStation();
			editLineInfo.setText(info);
			editStartStation.setText(lineInfo.getBeginStation());
			editEndStation.setText(lineInfo.getEndStation());
			
			btnSave.setOnClickListener(new SaveListener());

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("BusLineFavorFragment", e.getMessage());
			e.printStackTrace();
		}
		
		
		return view;
	}
	/**
	 * ���水ť����
	 * @author wang
	 *
	 */
	class SaveListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				saveFavorInfo();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
	}
	
	public void saveFavorInfo() throws SQLException
	{
		
//		Log.e("BusLinefavorFragment",Integer.toString(lineInfo.getCityCode()));
//		Log.e("BusLinefavorFragment",lineInfo.getLineCode());
//		Log.e("BusLinefavorFragment",lineInfo.getLineName());
//		Log.e("BusLinefavorFragment",lineInfo.getBeginStation());
//		Log.e("BusLinefavorFragment",lineInfo.getEndStation());
		
		Builder dlg = new AlertDialog.Builder(activity);
		dlg.setTitle("��ʾ");
		dlg.setPositiveButton("ȷ��", null);
		
		if (this.editName.getText().toString() == "")
		{
			dlg.setMessage("�ղ�������Ϊ�գ�");
			dlg.show();
			return;
		}
		FavorLineBean bean = new FavorLineBean();
		bean.setFavorName(this.editName.getText().toString());
		bean.setCityCode(this.lineInfo.getCityCode());
		bean.setLineCode(this.lineInfo.getLineCode());
		bean.setLineName(this.lineInfo.getLineName());
		bean.setBeginStation(this.lineInfo.getBeginStation());
		bean.setEndStation(this.lineInfo.getEndStation());
		bean.setLineSxx(this.sxx);
		boolean br = mBusMan.saveFavorLineInfo(bean);
		if (br)
		{
			dlg.setMessage("�ղر���ɹ���");
			dlg.show();
		}
		else
		{
			dlg.setMessage("�ղ����Ѵ��ڣ�");
			dlg.show();
			
			//messageBox();
		}
	}
	//����Fragment֪ͨ�л�tab
//	public interface changedTab{
//		public void changedTab(int i,String ch);
//	}

	

}
