export default {
  data() {
    return {
      value: 'ss',
      object: [
        {
          elementName: 'El-input',
          elementAttr: {
            style: {},
            attrs: {
              id: 'aa'
            },
            props: {
              myProp: 'bb'
            }
            , domProps: {},
            on: {},
            ref: 'my'
          },
        }
      ],
    }
  },
  methods: {},
  render: function (createElement) {
    let obj = this.object[0];
    let that = this;
    return createElement(obj.elementName, {
      style: {},
      attrs: {
        id: 'aa',
      },
      props: {
        myProp: 'bb'
      }
      , domProps: {
        value: that.value,
      },
      on: {
        input: function (event) {
          that.$emit('input', event.target.value)
        }
      },
      ref: 'my'
    })
  }
}
