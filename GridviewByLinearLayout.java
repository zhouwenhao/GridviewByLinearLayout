
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHOU on 2016/6/15.
 */
abstract public class GridviewByLinearLayout<T>
{
    protected LinearLayout mBgLinearLayout;
    protected int mNumByLine = 1;
    protected int mPx = 0;
    protected List<T> mDataList;
    protected List<View> mViewList;
    protected Context mContext;

    public GridviewByLinearLayout(Context context, int numByLine){
        this.mContext = context;
        this.mNumByLine = numByLine;
    }

    public GridviewByLinearLayout(Context context, int numByLine, int px){
        this.mContext = context;
        this.mNumByLine = numByLine;
        this.mPx = px;
    }

    public void reSetList(List<T> dataList){
        this.mDataList = dataList;

        mViewList = new ArrayList<View>(0);
        initView();
    }

    public List<T> getList(){
        return mDataList;
    }

    public void refresh(){
        int dataSize = getCount();
        int lineNum = getLineNum();
        for (int i = 0; i < lineNum; i++)
        {

            for (int j = 0; j < mNumByLine; j++)
            {
                int currentId = i * mNumByLine + j;
                if (currentId < dataSize)
                {
                    getView(mContext, currentId, mViewList.get(currentId));
                }
            }
        }
    }

    public GridviewByLinearLayout(Context context, List<T> dataList, int numByLine){
        this.mContext = context;
        this.mNumByLine = numByLine;
        this.mDataList = dataList;

        mViewList = new ArrayList<View>(0);
        initView();
    }

    public GridviewByLinearLayout(Context context, List<T> dataList, int numByLine, int px){
        this.mContext = context;
        this.mNumByLine = numByLine;
        this.mDataList = dataList;
        this.mPx = px;

        mViewList = new ArrayList<View>(0);
        initView();
    }

    public int getCount(){
        return mDataList.size();
    }

    public int getLineNum(){
        if (getCount() <= 0){
            return 0;
        }else {
            return (((getCount() - 1) / mNumByLine) + 1);
        }
    }

    public T getItem(int position){
        return mDataList.get(position);
    }

    private void initView(){
        if (mBgLinearLayout == null)
        {
            mBgLinearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.empty_linear, null);
        }
        mBgLinearLayout.removeAllViews();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f);
        LinearLayout.LayoutParams pxParam = new LinearLayout.LayoutParams(mPx, LinearLayout.LayoutParams.MATCH_PARENT);

        int dataSize = getCount();
        int lineNum = getLineNum();
        for (int i = 0; i < lineNum; i++){
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            if (mPx > 0){
                addPxView(layout, pxParam);
            }
            for (int j = 0; j < mNumByLine; j++){
                int currentId = i * mNumByLine + j;
                if (currentId < dataSize)
                {
                    View view = getView(mContext, currentId, null);
                    layout.addView(view, param);
                    mViewList.add(view);
                }else {
                    addPxView(layout, param);
                }
                if (mPx > 0){
                    addPxView(layout, pxParam);
                }
            }
            mBgLinearLayout.addView(layout);
        }
    }

    protected void addPxView(LinearLayout layout, LinearLayout.LayoutParams param){
        layout.addView(new TextView(mContext), param);
    }

    abstract protected View getView(final Context mContext, final int position, View view);



    public void setBgColor(int color){
        mBgLinearLayout.setBackgroundColor(color);
    }

    public LinearLayout getAllView(){
        return mBgLinearLayout;
    }

}
