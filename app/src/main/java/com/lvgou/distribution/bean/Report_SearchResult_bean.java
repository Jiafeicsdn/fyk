package com.lvgou.distribution.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class Report_SearchResult_bean {
    /**
     * status : 1
     * message : 查询成功
     * result : [{"CurrentPage":1,"TotalPages":4,"TotalItems":31,"ItemsPerPage":10,"Items":[{"ID":45,"ShopName":"测试1","Adderss":"测试的","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.276522","Longitude":"120.071085","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":44,"ShopName":"12345432","Adderss":"浙江省温州市温州市平阳县","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.303474","Longitude":"120.086473","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":43,"ShopName":"啊哈哈","Adderss":"浙江省杭州市西湖区文一西路776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.280498","Longitude":"120.094919","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":42,"ShopName":"湖南小龙虾测试2","Adderss":"广东省汕尾市广场1号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.299412","Longitude":"120.107738","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":41,"ShopName":"湖南小龙虾测试1","Adderss":"浙江省杭州市西湖区五一广场100号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":39,"ShopName":"杭州途购管家5","Adderss":"福建省杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":38,"ShopName":"杭州途购管家4","Adderss":"安徽省杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":37,"ShopName":"杭州途购管家3","Adderss":"江西省杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":36,"ShopName":"杭州途购管家2","Adderss":"西藏区杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":35,"ShopName":"杭州途购管家1","Adderss":"天津杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""}],"Context":null}]
     */

    public  int status;
    public String message;
    /**
     * CurrentPage : 1
     * TotalPages : 4
     * TotalItems : 31
     * ItemsPerPage : 10
     * Items : [{"ID":45,"ShopName":"测试1","Adderss":"测试的","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.276522","Longitude":"120.071085","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":44,"ShopName":"12345432","Adderss":"浙江省温州市温州市平阳县","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.303474","Longitude":"120.086473","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":43,"ShopName":"啊哈哈","Adderss":"浙江省杭州市西湖区文一西路776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.280498","Longitude":"120.094919","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":42,"ShopName":"湖南小龙虾测试2","Adderss":"广东省汕尾市广场1号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"30.299412","Longitude":"120.107738","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":41,"ShopName":"湖南小龙虾测试1","Adderss":"浙江省杭州市西湖区五一广场100号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":39,"ShopName":"杭州途购管家5","Adderss":"福建省杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":38,"ShopName":"杭州途购管家4","Adderss":"安徽省杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":37,"ShopName":"杭州途购管家3","Adderss":"江西省杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":36,"ShopName":"杭州途购管家2","Adderss":"西藏区杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""},{"ID":35,"ShopName":"杭州途购管家1","Adderss":"天津杭州市西湖区文一西路花蒋路口776号","CountryPath":"","State":0,"LoginName":"","PassWord":"","LinkName":"","Mobile":"","Latitude":"0","Longitude":"0","Business":"咖啡;巧克力;牛奶;手机","CreateTime":"1900-01-01T00:00:00","LastLoginTime":"1900-01-01T00:00:00","Intro":""}]
     * Context : null
     */

    public List<ResultBean> result;



    public static class ResultBean {
        public int CurrentPage;
        public int TotalPages;
        public int TotalItems;
        public int ItemsPerPage;
        public Object Context;
        /**
         * ID : 45
         * ShopName : 测试1
         * Adderss : 测试的
         * CountryPath :
         * State : 0
         * LoginName :
         * PassWord :
         * LinkName :
         * Mobile :
         * Latitude : 30.276522
         * Longitude : 120.071085
         * Business : 咖啡;巧克力;牛奶;手机
         * CreateTime : 1900-01-01T00:00:00
         * LastLoginTime : 1900-01-01T00:00:00
         * Intro :
         */

        public List<ItemsBean> Items;

        public static class ItemsBean {
            public int ID;
            public String ShopName;
            public String Adderss;
            public String CountryPath;
            public int State;
            public String LoginName;

            public ItemsBean(int ID, String shopName, String adderss, String countryPath, int state, String loginName, String passWord, String linkName, String mobile, String latitude, String longitude, String business, String createTime, String lastLoginTime, String intro) {
                this.ID = ID;
                ShopName = shopName;
                Adderss = adderss;
                CountryPath = countryPath;
                State = state;
                LoginName = loginName;
                PassWord = passWord;
                LinkName = linkName;
                Mobile = mobile;
                Latitude = latitude;
                Longitude = longitude;
                Business = business;
                CreateTime = createTime;
                LastLoginTime = lastLoginTime;
                Intro = intro;
            }

            public String PassWord;
            public String LinkName;
            public String Mobile;
            public String Latitude;
            public String Longitude;
            public String Business;
            public String CreateTime;
            public String LastLoginTime;
            public String Intro;


        }
    }
}
