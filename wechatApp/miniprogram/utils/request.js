const baseURL = 'http://localhost:8083'

// 请求封装
const request = (options) => {
  return new Promise((resolve, reject) => {

    const token = wx.getStorageSync('token')

    wx.request({

      url: baseURL + options.url,

      method: options.method || 'GET',

      data: options.data || {},


      header: {

        'Content-Type': 'application/json',

        // JWT规范写法
        'Authorization': token ? 'Bearer ' + token : ''

      },


      success: (res) => {


        if (res.statusCode === 200) {


          if (res.data.code === 200) {

            resolve(res.data)

          } else {

            reject(res.data.message || '请求失败')

          }


        } else {


          reject('网络错误：' + res.statusCode)


        }


      },


      fail: (err) => {

        reject(err)

      }

    })

  })
}


export default request