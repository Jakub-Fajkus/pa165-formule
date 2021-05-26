import componentCarDetail from '../components/componentCarDetail.js'
import componentCarComponents from '../components/componentCarComponents.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Car detail',
    requiredRoles: ["ROLE_MANAGER"],
    components: {componentCarDetail, componentCarComponents},

    props: {
        pageParams: Object,
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
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