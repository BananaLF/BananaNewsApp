package com.lf.newsapp.Net;

import com.lf.newsapp.Entity.ResultResponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * ***
 *
 * @author luozf
 * @version 1.0, 2016/10/11
 * @since 1.0
 */
public interface HttpService {
    /**
     * 当@GET或@POST注解的url为全路径时（可能和baseUrl不是一个域），会直接使用注解的url的域。
     * 如果请求为post实现，那么最好传递参数时使用@Field、@FieldMap和@FormUrlEncoded。
     * 因为@Query和或QueryMap都是将参数拼接在url后面的，而@Field或@FieldMap传递的参数时放在请求体的。
     * 使用@Path时，path对应的路径不能包含”/”，否则会将其转化为%2F。在遇到想动态的拼接多节url时，还是使用@Url吧。
     */
    /**GET方法**/

    /**
     * GET请求
     * @param path 资源路径
     * @param params 请求参数 可以为空
     * @return
     */
    @GET
    Observable<ResponseBody> getNetData(@Url String path, @QueryMap Map<String, String> params);

    /**
     * POST请求
     * @param path 资源路径
     * @param params 请求参数 可以为空
     * @return
     */
    @POST
    Observable<ResponseBody> postNetData(@Url String path, @QueryMap Map<String, String> params);

    /**
     * 单文件上传
     * @param path
     * @param requestBody
     * @return
     */
    @Multipart
    @POST
    Observable<ResponseBody> upLoadFile(
            @Url String path,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @POST
    Call<ResponseBody> uploadFiles(
            @Url String path,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    /**
     * 文件下载
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl, @QueryMap Map<String, String> params);

    /**视频处理新增(仅适用于于此项目)
     *
     */

    /**
     * 获取视频页的html代码
     */
    @GET
    Observable<ResponseBody> getVideoHtml(@Url String url);

    /**
     * 获取视频数据json
     * @param url
     * @return
     */
    @GET
    Observable<ResultResponse> getVideoData(@Url String url);
}
