-- luzhixue
local count = redis.call("GET", KEYS[1])

if  count == false then
	redis.call("incr",KEYS[1])
    redis.call("expire",KEYS[1],ARGV[1])
    return 1
else
	if tonumber(count) >= tonumber(ARGV[2]) then
    return 0
  else
    redis.call("incr",KEYS[1])
    return 1
   end
end