package com.odishakrushi.NetworkRelatedClass;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.odishakrushi.ModelClass.EventModel;
import com.odishakrushi.ModelClass.ResponseModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall {

    public static void fileUpload(String filePath,String qnaid,String mystoryid) {// ImageSenderInfo imageSenderInfo

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);
        Logger.addLogAdapter(new AndroidLogAdapter());

        File file = new File(filePath);
        //create RequestBody instance from file
       // RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        Gson gson = new Gson();
       // String patientData = gson.toJson(imageSenderInfo);

        RequestBody description = RequestBody.create(MultipartBody.FORM, qnaid);//patientData
        RequestBody description2 = RequestBody.create(MultipartBody.FORM, mystoryid);//patientData
        // finally, execute the request

        Call<ResponseModel> call = apiInterface.fileUpload(description,description2, body);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Logger.d("Response: " + response);

                ResponseModel responseModel = response.body();
                Logger.d("responseModel: " + responseModel);
                if(responseModel != null){
                    EventBus.getDefault().post(new EventModel("response", responseModel.getMessage()));
                    Logger.d("Response code " + response.code() +
                            " Response Message: " + responseModel.getMessage());
                } else
                    EventBus.getDefault().post(new EventModel("response","Something went wrong" ));//"ResponseModel is NULL" or String.valueOf(responseModel)
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Logger.d("Exception: " + t);
                EventBus.getDefault().post(new EventModel("response", t.getMessage()));
            }
        });
    }

}
