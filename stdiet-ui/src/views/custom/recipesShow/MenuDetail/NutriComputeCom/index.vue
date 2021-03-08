<template>
  <div class="nutri_compute_com_wrapper">
    <div class="header">{{ date }}</div>
    <div class="content">
      <section class="left">
        <div style="font-size: 18px">营养分析</div>
        <div class="total">
          <div>总热量约等于</div>
          <div>{{ totalHeat }}千卡</div>
        </div>
      </section>
      <section class="right">
        <table class="my_table" border="1">
          <thead>
            <tr>
              <th></th>
              <th>蛋白质</th>
              <th>脂肪</th>
              <th>碳水</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td style="font-size: 12px">摄入量</td>
              <td>{{ pWeight }}</td>
              <td>{{ fWeight }}</td>
              <td>{{ cWeight }}</td>
            </tr>
            <tr>
              <td style="font-size: 12px">供能比</td>
              <td>{{ pRate }}</td>
              <td>{{ fRate }}</td>
              <td>{{ cRate }}</td>
            </tr>
          </tbody>
        </table>
        <div class="mask" />
      </section>
    </div>
  </div>
</template>
<script>
export default {
  name: "nutriComputeCom",
  props: ["date", "value"],
  data() {
    return {
      totalHeat: 0,
      pWeight: "0克",
      fWeight: "0克",
      cWeight: "0克",
      pRate: "0%",
      fRate: "0%",
      cRate: "0%",
    };
  },
  watch: {
    value(val) {
      // console.log({ val });
      if (!val || !val.length) {
        return;
      }
      let pWeight = 0,
        fWeight = 0,
        cWeight = 0;

      val.forEach((dishes) => {
        dishes.igdList.forEach((igd) => {
          pWeight += (igd.proteinRatio * igd.weight) / 100;
          fWeight += (igd.fatRatio * igd.weight) / 100;
          cWeight += (igd.carbonRatio * igd.weight) / 100;
        });
      });
      this.totalHeat = (pWeight * 4 + fWeight * 9 + cWeight * 4).toFixed(1);
      this.pWeight = pWeight.toFixed(1) + "克";
      this.fWeight = fWeight.toFixed(1) + "克";
      this.cWeight = cWeight.toFixed(1) + "克";
      this.pRate = (((pWeight * 4) / this.totalHeat) * 100).toFixed(2) + "%";
      this.fRate = (((fWeight * 9) / this.totalHeat) * 100).toFixed(2) + "%";
      this.cRate = (((cWeight * 4) / this.totalHeat) * 100).toFixed(2) + "%";
    },
  },
};
</script>
<style lang="scss" scoped>
.nutri_compute_com_wrapper {
  padding: 10px 14px;
  background: #409eff;
  border-radius: 4px;
  color: white;
  .header {
    text-align: center;
    font-weight: bold;
  }

  .content {
    display: flex;
    margin-top: 12px;

    .left {
      padding-top: 10px;
      & > div:nth-child(1) {
        text-align: center;
        flex: 1;
      }

      .total {
        text-align: center;
        margin-top: 16px;
        border: 1px solid white;
        padding: 8px 16px;

        & > div:nth-child(1) {
          font-size: 12px;
          margin-bottom: 8px;
        }
      }
    }
    .right {
      flex: 2;
      border: 1px solid white;
      margin-left: 10px;
      padding: 4px;
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;

      .my_table {
        width: 100%;
        height: 100%;
        border-spacing: 0;
        border: 1px solid white;
        border-collapse: collapse;
        table-layout: fixed;

        th {
          font-size: 12px;
          text-align: center;
          height: 30px;
        }

        td {
          font-size: 10px;
          text-align: center;
          height: 30px;
        }
      }

      .mask {
        position: absolute;
        width: calc(100% - 8px);
        height: calc(100% - 8px);
        top: 4px;
        border: 1px solid #409eff;
      }
    }
  }
}
</style>
