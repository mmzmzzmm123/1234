const formatTime = date => {
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();
  const second = date.getSeconds();
  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':');
};

const formatNumber = n => {
  n = n.toString();
  return n[1] ? n : '0' + n;
};
function formatNumberHHMM(n){
	if(n<10) return '0' + n
	return n
}

Date.prototype.formatDate = function(){
	const year = this.getFullYear();
	const month = this.getMonth() + 1;
	const day = this.getDate();
	return [year, month, day].map(formatNumber).join('-')
}
Date.prototype.formatHHMM = function(){
	if(this.getTime() == 0){
		return '--'
	}
	const hours = this.getHours()
	const minutes = this.getMinutes()
	return formatNumberHHMM(hours)+':'+formatNumberHHMM(minutes)
}
Date.prototype.formatTime = function(){
	const year = this.getFullYear();
	const month = this.getMonth() + 1;
	const day = this.getDate();
	const hour = this.getHours();
	const minute = this.getMinutes();
	const second = this.getSeconds();
	return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':');
}
const MILLISE_MINUTE = 60 * 1000
const MILLISE_DAY = 24 * 60 * MILLISE_MINUTE
const UTC8_OFFSET = -8 * 60 * MILLISE_MINUTE
Date.prototype.addDay = function(day){
	return new Date(this.getTime() + day * MILLISE_DAY)
}
Date.prototype.getDateStart = function(){
	const time = this.getTime()
	return new Date(time - time % MILLISE_DAY + this.getTimezoneOffset() * MILLISE_MINUTE)
}
Date.fromString = function(str){
	if(str){
		if(str.length >= 24 || str[str.length - 1] == 'Z'){
			return new Date(str)
		}
		let date = new Date(str.replace(/-/g,'/'))
		return new Date(date.getTime() - (date.getTimezoneOffset() * MILLISE_MINUTE) + UTC8_OFFSET)
	}
	return new Date(null)
}

function minuteToHHMM(minutes){
	return Math.floor(minutes / 60) + ':' + minutes % 60
}

export default{
	formatTime: formatTime,
	minuteToHHMM: minuteToHHMM
}