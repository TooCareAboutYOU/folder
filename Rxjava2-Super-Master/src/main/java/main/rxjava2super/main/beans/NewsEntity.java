package main.rxjava2super.main.beans;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;


public class NewsEntity implements Serializable {


    private static final long serialVersionUID = -7696283081045609340L;
    /**
     * error : false
     * results : [{"_id":"5c0622429d2122308e7445cf","createdAt":"2018-12-04T06:44:18.364Z","desc":"一个基于ijkplayer的完整视频播放器封装，支持自定义，拓展性强，已经用于实际开发中","images":["http://img.gank.io/0cd8baa4-7d96-40fb-ab0c-4b3668a7ac4d","http://img.gank.io/35066fc9-4c67-498d-b9e1-f8e3ca7410e1","http://img.gank.io/ad8b369e-c643-4631-afdd-4466aab4f7fd","http://img.gank.io/101d45df-c66b-4610-809a-734fbca99967","http://img.gank.io/d8755a02-fe71-4562-ac9f-4d7d6b0d3358"],"publishedAt":"2019-02-13T03:26:06.640Z","source":"web","type":"Android","url":"https://github.com/yangchong211/YCVideoPlayer","used":true,"who":"fingdo"},{"_id":"5c491af39d212264d18bb279","createdAt":"2019-01-24T01:54:59.296Z","desc":"一款简单高效的滑动库，模仿MIUI滑动返回或即刻返回功能","images":["http://img.gank.io/3ac00581-11e4-4c2d-ba32-e3192d5840d0"],"publishedAt":"2019-02-13T02:44:43.299Z","source":"web","type":"Android","url":"https://github.com/dkzwm/EasySwipeLayout","used":true,"who":"lijinshanmx"},{"_id":"5c46e85d9d212264d4501d47","createdAt":"2019-02-13T02:41:46.573Z","desc":"在图片上做标签，图片可缩放。","images":["http://img.gank.io/c8aecd99-61a9-4634-aa91-1b7ab39081ab"],"publishedAt":"2019-02-13T02:41:56.484Z","source":"web","type":"Android","url":"https://github.com/lihangleo2/Imgdots","used":true,"who":"lijinshanmx"},{"_id":"5c4813179d212264d18bb278","createdAt":"2019-01-23T07:09:11.96Z","desc":"AppInit 是一款 Android 应用初始化框架，基于组件化的设计思路，功能灵活，使用简单。","images":["http://img.gank.io/7b10b382-7568-474d-bdab-7de0bb403615"],"publishedAt":"2019-02-12T17:45:33.73Z","source":"web","type":"Android","url":"https://github.com/Meituan-Dianping/AppInit","used":true,"who":"lijinshanmx"},{"_id":"5c2df1479d2122759a04b597","createdAt":"2019-01-03T11:25:59.115Z","desc":"Android 一键加入侧滑返回 (类似\u201c小米MIX\u201d和新版\u201c即刻\u201d滑动返回)","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fze96rdfhmg308w0ft7wh","https://ww1.sinaimg.cn/large/0073sXn7ly1fze96s6tdag308w0ftjvw"],"publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/qinci/AndroidSlideBack","used":true,"who":"qinci"},{"_id":"5c30271a9d2122759a04b59d","createdAt":"2019-01-05T03:40:10.216Z","desc":"一个漂亮的卡片式切换菜单","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fze96t2usdg30m80gogrr"],"publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/notice501/coolMenu","used":true,"who":"foocoder"},{"_id":"5c3089459d2122759d3e5ff4","createdAt":"2019-01-05T10:39:01.88Z","desc":"仿QQ图片发送挺炫效果的加载View,效果赞","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fze96wakcng30dc0ngb29"],"publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/hewking/HaloImageProgressView","used":true,"who":"hewking"},{"_id":"5c36dc329d212264ce006f29","createdAt":"2019-01-10T05:46:26.150Z","desc":"结合 Zxing Zbar，并处理优化的极速扫码","publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/nanchen2251/AiYaScanner","used":true,"who":"LiuShilin"},{"_id":"5c384b029d212264ce006f2d","createdAt":"2019-01-11T07:51:30.67Z","desc":"一起来复现网易云音乐引导页效果","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fze972ev1og309z0gok3u","https://ww1.sinaimg.cn/large/0073sXn7ly1fze973gq93g309c0godph"],"publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/wobiancao/Music163GuideDemo","used":true,"who":"兔子吃过窝边草"},{"_id":"5c3853279d212264d4501d23","createdAt":"2019-01-11T08:26:15.564Z","desc":"升级UETool，可查看修改任意安装App的布局参数","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fze97bnyefg30dc0o0kjn"],"publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/zhangke3016/VirtualUETool","used":true,"who":"张珂"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * _id : 5c0622429d2122308e7445cf
         * createdAt : 2018-12-04T06:44:18.364Z
         * desc : 一个基于ijkplayer的完整视频播放器封装，支持自定义，拓展性强，已经用于实际开发中
         * images : ["http://img.gank.io/0cd8baa4-7d96-40fb-ab0c-4b3668a7ac4d","http://img.gank.io/35066fc9-4c67-498d-b9e1-f8e3ca7410e1","http://img.gank.io/ad8b369e-c643-4631-afdd-4466aab4f7fd","http://img.gank.io/101d45df-c66b-4610-809a-734fbca99967","http://img.gank.io/d8755a02-fe71-4562-ac9f-4d7d6b0d3358"]
         * publishedAt : 2019-02-13T03:26:06.640Z
         * source : web
         * type : Android
         * url : https://github.com/yangchong211/YCVideoPlayer
         * used : true
         * who : fingdo
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
