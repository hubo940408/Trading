package personal.edu.trading.user;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class GoodsShop {

    /**
     * code : 1
     * msg :  success
     * datas : [{"price":"66","name":"单车","description":"......","page":"/images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG","type":"other","uuid":"5606FF8EF60146A48F1FCDC34144907D","master":"android"}]
     */

    private int code;
    private String msg;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * price : 66
         * name : 单车
         * description : ......
         * page : /images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG
         * type : other
         * uuid : 5606FF8EF60146A48F1FCDC34144907D
         * master : android
         */

        private String price;
        private String name;
        private String description;
        private String page;
        private String type;
        private String uuid;
        private String master;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }
    }
}
