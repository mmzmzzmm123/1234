<template>
  <div class="dashboard-editor-container"></div>
</template>

<script>
import { wxlogin } from "@/api/system/user";
import { getToken, setToken, removeToken } from "@/utils/auth";

export default {
  name: "wxLogin",
  data() {
    return {
      code: "",
    };
  },
  created() {
    this.code = this.$route.query.code;
    this.wxLogin();
  },
  methods: {
    wxLogin() {
      if (getToken()) {
        this.$router.push({ path: "/index?code=" + this.code });
        return;
      }
      if (this.code != "" && this.code != null) {
        //console.log("zhixing");
        wxlogin(this.code).then((res) => {
          console.log(res);
          if (res.code == "200") {
            if (res.token == "") {
                this.$router.push({ path: "/login" });
            } else {
              setToken(res.token);
              this.$router.push({ path: "/index" });
            }
          }
        });
      }
    },
  },
};
</script>