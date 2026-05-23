function repeater(text, tiefe)
if (tiefe <= 0) then return text end
return repeater(text .. text, tiefe-1)
end


print(repeater("BoT", 5))
