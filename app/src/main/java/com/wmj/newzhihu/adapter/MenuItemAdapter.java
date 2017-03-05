package com.wmj.newzhihu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmj.newzhihu.R;

import java.util.List;



/**
 * Created by wumingjun1 on 2017/2/19.
 */

public class MenuItemAdapter extends BaseAdapter
{
    private final int mIconSize;
    private LayoutInflater mInflater;
    private Context mContext;
    private Drawable.ConstantState[] ConstantStates = new Drawable.ConstantState[2];
    public MenuItemAdapter(Context context, List<LvMenuItem> Items)
    {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mItems =Items;
//        mItems.add(new LvMenuItem(R.drawable.ic_home,"首页"));

        mIconSize = context.getResources().getDimensionPixelSize(R.dimen.drawer_icon_size);//24dp
        ConstantStates[0] = mContext.getResources().getDrawable(R.drawable.ic_plus,null).getConstantState();
        ConstantStates[1] = mContext.getResources().getDrawable(R.drawable.ic_right,null).getConstantState();

    }

    private List<LvMenuItem> mItems ;

    @Override
    public int getCount()
    {
        return mItems.size();
    }


    @Override
    public Object getItem(int position)
    {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    @Override
    public int getItemViewType(int position)
    {
        return mItems.get(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LvMenuItem item = mItems.get(position);
        switch (item.type)
        {
            case LvMenuItem.TYPE_NORMAL:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_item, parent,
                            false);
                }
                TextView itemView = (TextView) convertView;
                itemView.setText(item.name);
                itemView.setTextColor(mContext.getResources().
                        getColorStateList(R.color.colorPrimary,null));
                Drawable icon = mContext.getResources().getDrawable(item.icon);
                //setIconColor(icon);
                if (icon != null)
                {
                    icon.setBounds(0, 0, mIconSize, mIconSize);
                    TextViewCompat.setCompoundDrawablesRelative(itemView, icon, null, null, null);
                }

                break;
            case LvMenuItem.TYPE_NO_ICON:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_item_subheader,
                            parent, false);
                }
                TextView subHeader = (TextView) convertView.findViewById(R.id.title);
                final ImageView iconImage = (ImageView) convertView.findViewById(R.id.plus_or_add);
                subHeader.setText(item.name);
                subHeader.setTextColor(Color.BLACK);
                iconImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Drawable.ConstantState c = iconImage.getDrawable().getConstantState();
                        if(c==ConstantStates[0]){
                            iconImage.setImageDrawable(mContext.getResources().
                                    getDrawable(R.drawable.ic_right,null));
                        }else{

                        }
//                        Drawable d =null;
//                        if(iconImage.getDrawable()==drawables[0]){
//                            d = drawables[1];
//                        }else{
//                            d = drawables[0];
//                        }
                    }
                });
                break;
            case LvMenuItem.TYPE_SEPARATOR:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_item_separator,
                            parent, false);
                }
                break;
        }

        return convertView;
    }

    public void setIconColor(Drawable icon)
    {
        int textColorSecondary = android.R.attr.textColorSecondary;
        TypedValue value = new TypedValue();
        if (!mContext.getTheme().resolveAttribute(textColorSecondary, value, true))
        {
            return;
        }
        int baseColor = mContext.getResources().getColor(value.resourceId);
        icon.setColorFilter(baseColor, PorterDuff.Mode.MULTIPLY);
    }
    static public class LvMenuItem
    {
        public LvMenuItem(int icon, String name)
        {
            this.icon = icon;
            this.name = name;

            if (icon == NO_ICON && TextUtils.isEmpty(name))
            {
                type = TYPE_SEPARATOR;
            } else if (icon == NO_ICON)
            {
                type = TYPE_NO_ICON;
            } else
            {
                type = TYPE_NORMAL;
            }

            if (type != TYPE_SEPARATOR && TextUtils.isEmpty(name))
            {
                throw new IllegalArgumentException("you need set a name for a non-SEPARATOR item");
            }

//        L.e(type + "");


        }

        public LvMenuItem(String name)
        {
            this(NO_ICON, name);
        }
        public LvMenuItem(String name,String id)
        {
            this(NO_ICON, name);
            setId(id);
        }

        public LvMenuItem()
        {
            this(null);
        }

        private static final int NO_ICON = 0;
        public static final int TYPE_NORMAL = 0;
        public static final int TYPE_NO_ICON = 1;
        public static final int TYPE_SEPARATOR = 2;

        int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;
        int icon;
        String id = "";

        public void setId(String id) {
            this.id = id;
        }


        public String getId() {
            return id;
        }
    }
}




