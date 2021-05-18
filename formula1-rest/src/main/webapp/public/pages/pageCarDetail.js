import componentCarDetail from '../components/componentCarDetail.js'
import componentCarComponents from '../components/componentCarComponents.js'
// import store from "../../store";

export default {
    name: 'Car detail',
    components: {componentCarDetail, componentCarComponents},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'Car detail'

        return {title}
    },

    mounted() {
        console.log(this.pageParams);
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              {{ title }} {{id}} {{pageParams.id}}
              <div class="row">
                <div class="col-md-4">
                  <componentCarDetail :id="pageParams.id"></componentCarDetail>
                </div>
                <div class="col-md-8">
                  <componentCarComponents :id="pageParams.id"></componentCarComponents>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};