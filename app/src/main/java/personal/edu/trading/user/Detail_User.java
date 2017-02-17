package personal.edu.trading.user;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class Detail_User {

    /**
     * code : 1
     * msg :  success
     * datas : {"uuid":"5606FF8EF60146A48F1FCDC34144907D","name":"货物","type":"other","price":"66","description":".......","master":"android","pages":[{"uuid":"5EDCF1DA855348AF8DAD4DBA418897EF","goodsuuid":"5606FF8EF60146A48F1FCDC34144907D","uri":"/images/D3228118230A430B77/5606FF8F1FCDC34144907D/F99E38F09A.JPEG","url":"http://192.168.1.37:8080/images/5606FF8EF60146A48F1FCDC34144907D/F99E38F09A.JPEG","name":"F99E38F09A.JPEG"}]}
     */

    private int code;
    private String msg;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * uuid : 5606FF8EF60146A48F1FCDC34144907D
         * name : 货物
         * type : other
         * price : 66
         * description : .......
         * master : android
         * pages : [{"uuid":"5EDCF1DA855348AF8DAD4DBA418897EF","goodsuuid":"5606FF8EF60146A48F1FCDC34144907D","uri":"/images/D3228118230A430B77/5606FF8F1FCDC34144907D/F99E38F09A.JPEG","url":"http://192.168.1.37:8080/images/5606FF8EF60146A48F1FCDC34144907D/F99E38F09A.JPEG","name":"F99E38F09A.JPEG"}]
         */

        private String uuid;
        private String name;
        private String type;
        private String price;
        private String description;
        private String master;
        private List<PagesBean> pages;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public List<PagesBean> getPages() {
            return pages;
        }

        public void setPages(List<PagesBean> pages) {
            this.pages = pages;
        }

        public static class PagesBean {
            /**
             * uuid : 5EDCF1DA855348AF8DAD4DBA418897EF
             * goodsuuid : 5606FF8EF60146A48F1FCDC34144907D
             * uri : /images/D3228118230A430B77/5606FF8F1FCDC34144907D/F99E38F09A.JPEG
             * url : http://192.168.1.37:8080/images/5606FF8EF60146A48F1FCDC34144907D/F99E38F09A.JPEG
             * name : F99E38F09A.JPEG
             */

            private String uuid;
            private String goodsuuid;
            private String uri;
            private String url;
            private String name;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getGoodsuuid() {
                return goodsuuid;
            }

            public void setGoodsuuid(String goodsuuid) {
                this.goodsuuid = goodsuuid;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
