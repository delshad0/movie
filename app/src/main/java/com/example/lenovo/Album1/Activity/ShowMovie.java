package com.example.lenovo.Album1.Activity.recyclerview.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lenovo.Album1.Activity.recyclerview.Adapter.MovieAdapter;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;
import com.example.lenovo.Album1.Activity.recyclerview.FourFragment;
import com.example.lenovo.Album1.Activity.recyclerview.ImageModel;
import com.example.lenovo.Album1.Activity.recyclerview.R;
import com.example.lenovo.Album1.Activity.recyclerview.SessionManager;
import com.example.lenovo.Album1.Activity.recyclerview.TreeFragment;

import java.util.ArrayList;
import java.util.List;

public class ShowMovie extends AppCompatActivity {

        // private RecyclerView recyclerView;
       //  private AlbumsAdapter adapter;
      //   private List<Album> albumList;
        // private List<ImageModel> movieList;
         //private MovieAdapter movieadapter;
         private SessionManager sessionManager;
         private SQLiteHandler db;
    private MovieAdapter movieadapter;

    ImageView menu;
         private static ViewPager mPager;
         private static int currentPage = 0;
         private static int NUM_PAGES = 0;
         private static ArrayList<ImageModel> imageModelArrayList;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            db=new SQLiteHandler(this);
            sessionManager= new SessionManager(getApplicationContext());
            imageModelArrayList = new ArrayList<>();
            //imageModelArrayList = populateList();
        viewPager = (ViewPager) findViewById(R.id.pager5);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
            //sendRequest();
           // setMenu();
        EditText search=(EditText) findViewById(R.id.edt_search1);
        search(search);

        TabLayout tab= (TabLayout) findViewById(R.id.tabs);
          /* ViewPager viewpager=(ViewPager)findViewById(R.id.pager);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Intent intent=new Intent(ShowMovie.this,MainActivity.class);
                intent.putExtra("pos",position);
                startActivity(intent);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
          //  recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
           // movieList =new ArrayList<>();
           // albumList = new ArrayList<>();
           // adapter = new AlbumsAdapter(this, albumList, new ClickListener() {
           //     @Override
           //     public void clicklistener1(View view, int pos) {
//
                  //  db.deleteAllCart();

            //    }

             ////   @Override
             ////   public void clicklistener2(View view, int pos) {

              //  }

           // });

          //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
          //  recyclerView.setLayoutManager(mLayoutManager);
          //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
          //  recyclerView.setItemAnimator(new DefaultItemAnimator());
          //  recyclerView.setAdapter(adapter);
            //prepareAlbums();

       /*     try {
                Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
       }


    private void search(EditText search) {
        Log.i("leyla", String.valueOf(search));

        //final String search_text=search.getText().toString();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                movieadapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        /*

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                movieadapter.getFilter().filter(newText);
                return true;
            }
        });*/
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFrag(new FiveFragment(2), "رایگان");
        adapter.addFrag(new FourFragment(1), "انیمیشن");
        //adapter.addFrag(new TwoFragment(0), "سریال");
        adapter.addFrag(new TreeFragment(3), "فیلم");
        //adapter.addFrag(new OneFragment(), "خانه");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void setMenu() {


       /* menu = (ImageView) findViewById(R.id.menu1);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu p=new PopupMenu(ShowMovie.this,menu);

                getMenuInflater().inflate(R.menu.menu_album,p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout1:
                                logoutUser();

                                break;

                            default:
                                break;
                        }
                        return true;

                    }
                });
                p.show();
            }
        });
    }*/


    /*private void init() {
        mPager = (ViewPager) findViewById(R.id.pager1);
        mPager.setAdapter(new SliderPagerAdapter(ShowMovie.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator1);

        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;

         //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();
         Log.i("num", String.valueOf(NUM_PAGES));
        // Auto start of viewpager
        final android.os.Handler handler = new android.os.Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }*/
    }
    private void logoutUser() {

        sessionManager.setLogin(false);
        db.deleteAllCart();
        Intent intent = new Intent(ShowMovie.this, Login.class);
        startActivity(intent);
        finish();
    }

    /*private void sendRequest(){
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(AppConfig.slider_URL,
                null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                //for (int i = 0; i < response.length(); i++) {

                    try {
                            imageModelArrayList.add(new ImageModel(response.getString("s1")));
                            imageModelArrayList.add(new ImageModel(response.getString("s2")));
                            imageModelArrayList.add(new ImageModel(response.getString("s3")));
                        init();
                      //  }


                    } catch (JSONException e) {
                        e.printStackTrace();
                  //  }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " +"ERROR");
                Toast.makeText(getApplicationContext(),
                        parsFailure(error.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }*/

   /* private void prepareAlbums() {
            int[] covers = new int[]{
                    R.drawable.album1,
                    R.drawable.album2,
                    R.drawable.switzerland,
                    R.drawable.album4,
                    R.drawable.album5,
                    R.drawable.album6,
                    R.drawable.album7,
                    R.drawable.album8,

            };

            Album a = new Album("تاریخی", covers[0]);
            albumList.add(a);

            a = new Album("پلیسی و اکشن", covers[1]);
            albumList.add(a);

            a = new Album("خانوادگی", covers[2]);
            albumList.add(a);

            a = new Album("ترسناک", covers[3]);
            albumList.add(a);

            a = new Album("عاشقانه", covers[4]);
            albumList.add(a);

            a = new Album("مستند", covers[5]);
            albumList.add(a);

            a = new Album("تخیلی", covers[6]);
            albumList.add(a);

            a = new Album("کمدی",covers[7]);
            albumList.add(a);
            adapter.notifyDataSetChanged();
        }
*/

        public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

            private int spanCount;
            private int spacing;
            private boolean includeEdge;

            public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
                this.spanCount = spanCount;
                this.spacing = spacing;
                this.includeEdge = includeEdge;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int column = position % spanCount; // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }
            }
        }


        private int dpToPx(int dp) {
            Resources r = getResources();
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }

    }

