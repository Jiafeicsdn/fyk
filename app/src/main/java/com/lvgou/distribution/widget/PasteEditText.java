package com.lvgou.distribution.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/9/18.
 */
public class PasteEditText extends EditText{
    private Context context;

    public PasteEditText(Context paramContext)
    {
        super(paramContext);
        this.context = paramContext;
    }

    public PasteEditText(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        this.context = paramContext;
    }

    public PasteEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        this.context = paramContext;
    }

    protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
        if ((!TextUtils.isEmpty(paramCharSequence)) && (paramCharSequence.toString().startsWith("EASEMOBIMG")))
            setText("");
        super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    }

    @SuppressLint({"NewApi"})
    public boolean onTextContextMenuItem(int paramInt)
    {
        if (paramInt == 16908322)
        {
            ClipboardManager localClipboardManager = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            if ((localClipboardManager == null) || (localClipboardManager.getText() == null))
                return false;
            String str1 = localClipboardManager.getText().toString();
            if (str1.startsWith("EASEMOBIMG"))
            {
                String str2 = str1.replace("EASEMOBIMG", "");
//                Intent localIntent = new Intent(this.context, AlertDialog.class);
//                localIntent.putExtra("title", this.context.getResources().getString(2131296666));
//                localIntent.putExtra("forwardImage", str2);
//                localIntent.putExtra("cancel", true);
//                ((Activity)this.context).startActivityForResult(localIntent, 11);
            }
        }
        return super.onTextContextMenuItem(paramInt);
    }
}
