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

export default {
	formatSecondsCH,
	formatTime
}