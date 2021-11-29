package com.sososhopping.customer.purchase.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.textValidate.NameWatcher;
import com.sososhopping.customer.common.textValidate.PhoneWatcher;
import com.sososhopping.customer.purchase.viewmodel.PurchaseViewModel;
import com.sososhopping.customer.common.DateFormatMethod;
import com.sososhopping.customer.common.types.enumType.OrderType;
import com.sososhopping.customer.databinding.PurchaseMainBinding;
import com.sososhopping.customer.mysoso.model.MyInfoModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

@Getter
public class PurchaseFragment_Visit {

    PurchaseMainBinding binding;
    PurchaseViewModel purchaseViewModel;
    Context context;

    ArrayList<TextInputLayout> delivery;
    ArrayList<TextInputLayout> visit;

    public PurchaseFragment_Visit(PurchaseMainBinding binding, PurchaseViewModel purchaseViewModel, Context context) {
        this.binding = binding;
        this.purchaseViewModel = purchaseViewModel;
        this.context = context;

        visit = new ArrayList<>();
        visit.add(binding.includeLayoutVisit.textFieldName);
        visit.add(binding.includeLayoutVisit.textFieldPhone);
        visit.add(binding.includeLayoutVisit.textFieldDate);
        visit.add(binding.includeLayoutVisit.textFieldTime);

        delivery = new ArrayList<>();
        delivery.add(binding.includeLayoutVisit.textFieldDeliveryName);
        delivery.add(binding.includeLayoutVisit.textFieldDeliveryPhone);
        delivery.add(binding.includeLayoutVisit.textFieldRoadAddress);
        delivery.add(binding.includeLayoutVisit.textFieldDetailAddress);
    }

    private void resetInput(int id){
        ArrayList<TextInputLayout> target;
        if(id == R.id.button_visit){
            target = delivery;
        }
        else{
            target = visit;
        }
        for(TextInputLayout t : target) {
            t.getEditText().setText(null);
            t.setError(null);
            t.setErrorEnabled(false);
        }
    }

    protected String checkInput(Resources rs){
        ArrayList<TextInputLayout> target;
        int id = binding.includeLayoutVisit.toggleButton.getCheckedButtonId();

        if(id == R.id.button_visit){
            target = visit;
        }
        else{
            target = delivery;
        }

        for(TextInputLayout t : target){
            if(TextUtils.isEmpty(t.getEditText().getText())){
                return rs.getString(R.string.final_visit_empty);
            }
            if(t.getError() != null){
                return rs.getString(R.string.final_process_error);
            }
        }
        return null;
    }

    protected void setVisitLayout(FragmentManager fm, Resources rs) {
        binding.includeLayoutVisit.layoutDelivery.setVisibility(View.GONE);
        binding.includeLayoutVisit.toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                switch (checkedId) {
                    case R.id.button_visit:
                        if (isChecked) {
                            binding.includeLayoutVisit.layoutDelivery.setVisibility(View.GONE);
                            binding.includeLayoutVisit.layoutVisit.setVisibility(View.VISIBLE);


                            //배달비 제거
                            purchaseViewModel.getDeliveryPrice().setValue(0);
                            purchaseViewModel.setOrderType(OrderType.ONSITE);
                        }
                        break;

                    case R.id.button_delivery:
                        if (isChecked) {
                            binding.includeLayoutVisit.layoutVisit.setVisibility(View.GONE);
                            binding.includeLayoutVisit.layoutDelivery.setVisibility(View.VISIBLE);

                            //배달비 추가
                            purchaseViewModel.getDeliveryPrice().setValue(2500);
                            purchaseViewModel.setOrderType(OrderType.DELIVERY);
                        }
                        break;
                }
                resetInput(checkedId);
            }
        });

        //방문예약 입력 검증
        binding.includeLayoutVisit.editTextName.addTextChangedListener(
                new NameWatcher(binding.includeLayoutVisit.textFieldName, rs.getString(R.string.signup_error_name)));

        binding.includeLayoutVisit.editTextPhone.addTextChangedListener(
                new PhoneWatcher(binding.includeLayoutVisit.textFieldPhone, rs.getString(R.string.signup_error_phone))
        );

        binding.includeLayoutVisit.editTextDate.setEnabled(false);




        //달력 클릭 시 날짜 선택
        binding.includeLayoutVisit.textFieldDate.setEndIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                DatePickerDialog dialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                binding.includeLayoutVisit.editTextDate.setText(
                                        DateFormatMethod.dateFormat(year, monthOfYear, dayOfMonth)
                                );
                            }
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));

                //주문시각 30분 뒤부터 가능
                cal.add(Calendar.MINUTE, 30);
                dialog.setMinDate(cal);

                //7일간 가능
                for (int i = 0; i < 8; i++) {
                    if (!checkUnAvailableDate(cal.get(Calendar.DAY_OF_WEEK))) {
                        Calendar[] c = new Calendar[1];
                        c[0] = cal;
                        dialog.setDisabledDays(c);
                    }

                    if (i == 7) break;
                    cal.add(Calendar.DATE, 1);
                }
                dialog.setMaxDate(cal);
                dialog.show(fm, "픽업 날짜");
            }
        });

        binding.includeLayoutVisit.editTextTime.setEnabled(false);
        binding.includeLayoutVisit.textFieldTime.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.includeLayoutVisit.editTextDate.getText())) {
                    Toast.makeText(context, "날짜를 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar c = Calendar.getInstance();
                TimePickerDialog dialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                binding.includeLayoutVisit.editTextTime.setText(
                                        DateFormatMethod.timeFormat(hourOfDay, minute)
                                );
                            }
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true
                );
                SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                Timepoint[] t = null;
                try {
                    Date d = sf.parse(String.valueOf(binding.includeLayoutVisit.editTextDate.getText()));
                    c.setTime(d);
                    t = getSelectableTime(c.get(Calendar.DAY_OF_WEEK));

                    if (t != null) {
                        dialog.setMinTime(t[0]);
                        dialog.setMaxTime(t[1]);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dialog.show(fm, "픽업 시간");
            }
        });
    }

    private void setDateAndTime(DatePickerDialog dateDialog, TimePickerDialog timeDialog){
        Calendar cal = Calendar.getInstance();

        //주문시각 30분 뒤부터 가능
        cal.add(Calendar.MINUTE, 30);
        Date current = cal.getTime();

        try{
            if(purchaseViewModel.getShopInfo().getValue() != null){

                int weekday = dayOfWeekSwitcher(cal.get(Calendar.DAY_OF_WEEK));

                int minTime = Integer.parseInt(new SimpleDateFormat("HHmm").format(cal.getTime()));
                int minTimeBus = Integer.parseInt(purchaseViewModel.getShopInfo().getValue().getBusinessDays()
                        .get(weekday).getOpenTime());

                int start = Math.max(minTime, minTimeBus);
                int end = Integer.parseInt(purchaseViewModel.getShopInfo().getValue().getBusinessDays().get(weekday).getCloseTime());

                //이날은 못 하는날
                if(start >= end){
                    cal.add(Calendar.DATE,1);
                    dateDialog.setMinDate(cal);
                }

            }
        }catch (Exception e){

        }




    }

    protected void setDeliveryLayout(Resources rs){
        //배달예약 입력 검증
        binding.includeLayoutVisit.editTextDeliveryName.addTextChangedListener(
                new NameWatcher(binding.includeLayoutVisit.textFieldDeliveryName, rs.getString(R.string.signup_error_name))
        );
        binding.includeLayoutVisit.editTextDeliveryPhone.addTextChangedListener(
                new PhoneWatcher(binding.includeLayoutVisit.textFieldDeliveryPhone, rs.getString(R.string.signup_error_phone))
        );

        binding.includeLayoutVisit.checkBoxMyInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(purchaseViewModel.getMyInfo().getValue() != null){
                        MyInfoModel md = purchaseViewModel.getMyInfo().getValue();
                        if(md.getName() != null){
                            binding.includeLayoutVisit.editTextDeliveryName.setText(md.getName());
                        }
                        if(md.getPhone() != null){
                            binding.includeLayoutVisit.editTextDeliveryPhone.setText(md.getPhone());
                        }
                        if(md.getStreetAddress() != null){
                            binding.includeLayoutVisit.editTextRoadAddress.setText(md.getStreetAddress());
                        }
                        if(md.getDetailedAddress() != null){
                            binding.includeLayoutVisit.editTextDetailAddress.setText(md.getDetailedAddress());
                        }
                    }
                }
                else{
                    binding.includeLayoutVisit.editTextDeliveryName.setText(null);
                    binding.includeLayoutVisit.editTextDeliveryPhone.setText(null);
                    binding.includeLayoutVisit.editTextRoadAddress.setText(null);
                    binding.includeLayoutVisit.editTextDetailAddress.setText(null);
                }
            }
        });
    }

    public int dayOfWeekSwitcher(int dayOfWeek){
        if(dayOfWeek == Calendar.SUNDAY){
            dayOfWeek = 6;
        }
        else{
            dayOfWeek = dayOfWeek -2;
        }
        return dayOfWeek;
    }

    public boolean checkUnAvailableDate(int dayOfWeek){
        dayOfWeek = dayOfWeekSwitcher(dayOfWeek);
        try{
            if(purchaseViewModel.getShopInfo().getValue() != null){
                return purchaseViewModel.getShopInfo().getValue().getBusinessDays().get(dayOfWeek).getIsOpen();
            }
        }catch (Exception e){
        }
        return true;
    }

    public Timepoint[] getSelectableTime(int dayOfWeek){
        dayOfWeek = dayOfWeekSwitcher(dayOfWeek);
        String start, end;

        Calendar calendar = Calendar.getInstance();
        //30분 추가
        calendar.add(Calendar.MINUTE,30);

        try{
            if(purchaseViewModel.getShopInfo().getValue() != null){
                int minTime = Integer.parseInt(new SimpleDateFormat("HHmm").format(calendar.getTime()));
                int minTimeBus = Integer.parseInt(purchaseViewModel.getShopInfo().getValue().getBusinessDays().get(dayOfWeek).getOpenTime());

                start = Integer.toString(Math.max(minTime, minTimeBus));


                int startH = Integer.parseInt(start.substring(0,2));
                int startM = Integer.parseInt(start.substring(2,4));

                end = purchaseViewModel.getShopInfo().getValue().getBusinessDays().get(dayOfWeek).getCloseTime();

                int endH = Integer.parseInt(end.substring(0,2));
                int endM = Integer.parseInt(end.substring(2,4));

                Log.e("times", minTime + " " + minTimeBus + " " + start + " " +end);

                Timepoint[] t = new Timepoint[2];
                t[0] = new Timepoint(startH, startM);
                t[1] = new Timepoint(endH, endM);
                return t;
            }
        }catch (Exception e){
        }
        return null;
    }
}
