package com.swampass.nauticalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.swampass.nauticalapp.R;
import com.swampass.nauticalapp.model.Active_Chat;
import com.swampass.nauticalapp.model.CircleTransform;


public class ConnectionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    TextView person_name,person_email;
    RecyclerView recyclerView;
    DatabaseReference mRef;
    public FirebaseRecyclerAdapter<Active_Chat, Show_Chat_ViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_connections);

        //Database
        mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.keepSynced(true);



        //Recycler View
        recyclerView = (RecyclerView)findViewById(R.id.show_chat_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(ConnectionsActivity.this);
        //mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //VIEWS

        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        ImageView profileActivity = (ImageView) toolbar.findViewById(R.id.action_profile);
        ImageView homeActivity = (ImageView) toolbar.findViewById(R.id.action_home);
        progressBar = (ProgressBar) findViewById(R.id.progressDick);
        profileActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(ConnectionsActivity.this, ProfileActivity.class);
                startActivity(profileActivity);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });
        homeActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent home = new Intent(ConnectionsActivity.this, HomeActivity.class);
                startActivity(home);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_gaynigger, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        //Log.d("LOGGED", "Will Start Calling populateViewHolder : ");
        //Log.d("LOGGED", "IN onStart ");


        mFirebaseAdapter = new FirebaseRecyclerAdapter<Active_Chat, Show_Chat_ViewHolder>(Active_Chat.class, R.layout.chat_single_item, Show_Chat_ViewHolder.class, mRef) {

            public void populateViewHolder(final Show_Chat_ViewHolder viewHolder, Active_Chat model, final int position) {
                //Log.d("LOGGED", "populateViewHolder Called: ");
                progressBar.setVisibility(ProgressBar.INVISIBLE);

                if (!model.getName().equals("Null")) {
                    viewHolder.Person_Name(model.getName());
                    viewHolder.Person_Image(model.getImage_Url());
                    //viewHolder.Person_Email(model.getEmail());
                    if(model.getEmail().equals(SignIn.LoggedIn_User_Email))
                    {
                        //viewHolder.itemView.setVisibility(View.GONE);
                        viewHolder.Layout_hide();

                        //recyclerView.getChildAdapterPosition(viewHolder.itemView.getRootView());
                        // viewHolder.itemView.set;


                    }
                    else
                        viewHolder.Person_Email(model.getEmail());
                }


                //OnClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String retrieve_name = dataSnapshot.child("Name").getValue(String.class);
                                String retrieve_Email = dataSnapshot.child("Email").getValue(String.class);
                                String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);



                                Intent intent = new Intent(getApplicationContext(), ChatConversationActivity.class);
                                intent.putExtra("image_id", retrieve_url);
                                intent.putExtra("email", retrieve_Email);
                                intent.putExtra("name", retrieve_name);

                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };

        recyclerView.setAdapter(mFirebaseAdapter);





    }

    //View Holder For Recycler View
    public static class Show_Chat_ViewHolder extends RecyclerView.ViewHolder {
        private final TextView person_name, person_email;
        private final ImageView person_image;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public Show_Chat_ViewHolder(final View itemView) {
            super(itemView);
            person_name = (TextView) itemView.findViewById(R.id.chat_persion_name);
            person_email = (TextView) itemView.findViewById(R.id.chat_persion_email);
            person_image = (ImageView) itemView.findViewById(R.id.chat_persion_image);
            layout = (LinearLayout)itemView.findViewById(R.id.show_chat_single_item_layout);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        private void Person_Name(String title) {
            // Log.d("LOGGED", "Setting Name: ");
            person_name.setText(title);
        }
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params);
            layout.setLayoutParams(params);

        }


        private void Person_Email(String title) {
            person_email.setText(title);
        }


        private void Person_Image(String url) {

            if (!url.equals("Null")) {
                Glide.with(itemView.getContext())
                        .load(url)
                        .crossFade()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.loading)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(person_image);
            }

        }

    }
