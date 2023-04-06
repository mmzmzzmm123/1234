export function formatSecondsCH(seconds) {
	const hour = Math.floor(seconds / 3600)
	if (hour == 0) {
		const minute = Math.floor((seconds - hour * 3600) / 60)
		return minute + "分钟"
	}
	const minute = Math.floor((seconds - hour * 3600) / 60)
	return hour + "小时" + minute + "分钟"
}


export function formatTime(time) {
	let date = new Date(time);
	let y = date.getFullYear();
	let MM = date.getMonth() + 1;
	MM = MM < 10 ? "0" + MM : MM;
	let d = date.getDate();
	d = d < 10 ? "0" + d : d;
	let H = date.getHours();
	H = H < 10 ? "0" + H : H;
	let m = date.getMinutes();
	m = m < 10 ? "0" + m : m;
	let s = date.getSeconds();
	s = s < 10 ? "0" + s : s;
	return y + "-" + MM + "-" + d + " " + H + ":" + m + ':' + s;
}

// 将时长秒数转为分钟：秒的格式
export function formatDuration(seconds) {
  let min = Math.floor(seconds / 60)
  let sec = seconds - min * 60
  if (sec < 10) {
    sec = "0" + sec
  }
  if (min < 10) {
    return "0" + min + ":" + sec
  }
  return min + ":" + sec
}
    
export default {
	formatSecondsCH,
	formatTime,
  formatDuration
}