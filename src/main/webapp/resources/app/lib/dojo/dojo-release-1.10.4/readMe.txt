         Go to dojo_mod.js (dojo.js) search for 

	if (/^require\*/.test(module.mid)) {
				delete modules[module.mid];
			}
and append bellow it.

//add for Durandal. ====== HOPE
                        signal("moduleLoaded", [module.result, module.mid]);

