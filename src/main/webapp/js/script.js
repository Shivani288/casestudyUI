var connectionCount = 1;
var validateInputCount = 10;

function sendRequest(data, resultID) {
	$.ajax({
		method : 'post',
		url : 'ValidationServlet',
		data : data,
		datatype : 'json',
		success : function(responseText) {
			$(resultID).html(responseText);
		}
	});
}
function validateInput() {
	var errorMsg = "";
	if ($('#sourceType').val().length <= 0) {
		errorMsg = errorMsg + "</br>Source type cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#sourceDb').val().length <= 0) {
		errorMsg = errorMsg + "</br>Source database name cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#sourceUser').val().length <= 0) {
		errorMsg = errorMsg + "</br>Source user name cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#sourcePassword').val().length <= 0) {
		errorMsg = errorMsg + "</br>Source password cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#sourceTable').val().length <= 0) {
		errorMsg = errorMsg + "</br>Source table cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#destinationType').val().length <= 0) {
		errorMsg = errorMsg + "</br>Target type cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#destinationDb').val().length <= 0) {
		errorMsg = errorMsg + "</br>Target database name cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#destinationUser').val().length <= 0) {
		errorMsg = errorMsg + "</br>Target user name cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#destinationPassword').val().length <= 0) {
		errorMsg = errorMsg + "</br>Target password cannot be blank";
	} else {
		validateInputCount--;
	}
	if ($('#destinationTable').val().length <= 0) {
		errorMsg = errorMsg + "</br>Target table cannot be blank";
	} else {
		validateInputCount--;
	}
	$("#connectionResult").html(errorMsg + "</br>");
}
function connectionCreated() {
	$("#sourceType").prop('disabled', true);
	$("#sourceDb").prop('disabled', true);
	$("#sourceUser").prop('disabled', true);
	$("#sourcePassword").prop('disabled', true);
	$("#sourceTable").prop('disabled', true);
	$("#destinationType").prop('disabled', true);
	$("#destinationDb").prop('disabled', true);
	$("#destinationUser").prop('disabled', true);
	$("#destinationPassword").prop('disabled', true);
	$("#destinationTable").prop('disabled', true);
	$("#connector").prop('disabled', true);
}

function table() {
	if ($('#assertValueTable').val() == "source") {
		console.log($('#sourceTable').val());
		return $('#sourceTable').val();
	} else if ($('#assertValueTable').val() == "target") {
		console.log($('#destinationTable').val());
		return $('#destinationTable').val();
	}
}

$(function() {
	$("#connector").click(function() {
		if (connectionCount == 1) {
			var data = {
				validationType : 'connnection',
				sourceType : $('#sourceType').val(),
				sourceDbName : $('#sourceDb').val(),
				sourceUser : $('#sourceUser').val(),
				sourcePassword : $('#sourcePassword').val(),
				sourceTable : $('#sourceTable').val(),
				targetType : $('#destinationType').val(),
				targetDbName : $('#destinationDb').val(),
				targetUser : $('#destinationUser').val(),
				targetPassword : $('#destinationPassword').val(),
				targetTable : $('#destinationTable').val()
			};
			var resultID = "#connectionResult";
			sendRequest(data, resultID);
			connectionCreated();
			connectionCount--;
		} else {
			alert("Already Connected");
		}
	});
});

$(function() {
	$("#matchColCount").click(function() {
		if ($(this).is(":checked")) {
			if (connectionCount == 0) {
				var data = {
					validationType : 'matchColCount',
				};
				var resultID = "#colCountValidationResult";
				sendRequest(data, resultID);
			} else {
				alert("Establish connection first");
				$('#matchColCount').prop('checked', false);
			}
		} else {
			$("#colCountValidationResult").html("");
			$('#matchColCount').prop('checked', false);
		}
	});
});

$(function() {
	$("#matchColDataTypes").click(function() {
		if ($(this).is(":checked")) {
			if (connectionCount == 0) {
				var data = {
					validationType : 'matchColumnDataTypes',
				};
				var resultID = "#colDataTypesValidationResult";
				sendRequest(data, resultID);
			} else {
				alert("Establish connection first");
				$('#matchColDataTypes').prop('checked', false);
			}
		} else {
			$("#colDataTypesValidationResult").html("");
			$('#matchColDataTypes').prop('checked', false);
		}
	});
});

$(function() {
	$("#rowByRowValidation").click(function() {
		if ($(this).is(":checked")) {
			if (connectionCount == 0) {
				var data = {
					validationType : 'rowByRowValiation',
				};
				var resultID = "#rowByRowValidationResult";
				sendRequest(data, resultID);
			} else {
				alert("Establish connection first");
				$('#rowByRowValidation').prop('checked', false);
			}
		} else {
			$("#rowByRowValidationResult").html("");
			$('#rowByRowValidation').prop('checked', false);
		}
	});
});

$(document).ready(
		function() {
			$("#sampleLimit").keydown(
					function(e) {
						// Allow: backspace, delete, tab, escape, enter and .
						if ($
								.inArray(e.keyCode, [ 46, 8, 9, 27, 13, 110,
										190 ]) !== -1) {
							// let it happen, don't do anything
							return;
						}
						// Ensure that it is a number and stop the keypress
						if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57))
								&& (e.keyCode < 96 || e.keyCode > 105)) {
							e.preventDefault();
						}
					});
		});

$(function() {
	$("#sampleValidation").click(function() {
		var inputLength = $("#sampleLimit").val().length;
		if ($(this).is(":checked")) {
			if (inputLength > 0) {
				if (connectionCount == 0) {
					var data = {
						validationType : 'sampleValidation',
						sampleLimit : $("#sampleLimit").val(),
					};
					var resultID = "#sampleValidationResult";
					sendRequest(data, resultID);
				} else {
					alert("Establish connection first");
					$('#sampleValidation').prop('checked', false);
				}
			} else {
				alert("Enter Sample limit first");
				$('#sampleValidation').prop('checked', false);
			}
		} else {
			$("#sampleValidationResult").html("");
			$("#sampleLimit").val("");
		}
	});
});

$(function() {
	$("#customQueryValidation").click(function() {
		if ($(this).is(":checked")) {
			if (connectionCount == 0) {
				$("#customMenuTable").css({
					"display" : "block",
					"visibility" : "visible"
				});
			} else {
				alert("Establish connection first");
				$('#customQueryValidation').prop('checked', false);
			}
		} else {
			$("#customMenuTable").css({
				"display" : "none",
				"visibility" : "hidden"
			});
			$('#customQueryValidation').prop('checked', false);
		}
	});
});

$(function() {
	$("#outputValidation").click(function() {
		if ($(this).is(":checked")) {
			if (connectionCount == 0) {
				if ($("#customQueryValidation").is(":checked")) {
					var data = {
						validationType : 'outputValidation',
						sourceSql : $("textarea#sourceCustomQuery").val(),
						targetSql : $("textarea#destinationCustomQuery").val(),
					};
					var resultID = "#customQueryOutputValidationResult";
					sendRequest(data, resultID);
				} else {
					alert("Check custom validation menu");
					$('#outputValidation').prop('checked', false);
				}
			} else {
				alert("Establish connection first");
				$('#outputValidation').prop('checked', false);
			}
		} else {
			$("#customQueryOutputValidationResult").html("");
			$('#outputValidation').prop('checked', false);
		}
	});
});

$(function() {
	$("#assertValueValidation")
			.click(
					function() {
						if ($(this).is(":checked")) {
							if (connectionCount == 0) {
								if ($("#customQueryValidation").is(":checked")) {
									var input = $("#assertValue").val().length;
									if (input > 0) {
										var data = {
											validationType : 'assertValueValidation',
											sourceSql : $(
													"textarea#sourceCustomQuery")
													.val(),
											targetSql : $(
													"textarea#destinationCustomQuery")
													.val(),
											assertValue : $("#assertValue")
													.val(),
											table : table(),
										};
										var resultID = "#customQueryAssertValueValidationResult";
										sendRequest(data, resultID);
									} else {
										alert("Input assert value first");
										$('#assertValueValidation').prop(
												'checked', false);
									}
								} else {
									alert("Check custom validation menu");
									$('#assertValueValidation').prop('checked',
											false);
								}
							} else {
								alert("Establish connection first");
								$('#assertValueValidation').prop('checked',
										false);
							}
						} else {
							$("#customQueryAssertValueValidationResult").html(
									"");
							$('#assertValueValidation').prop('checked', false);
						}
					});
});

$(function() {
	$("#resultCountValidation").click(function() {
		if ($(this).is(":checked")) {
			if (connectionCount == 0) {
				if ($("#customQueryValidation").is(":checked")) {
					var data = {
						validationType : 'resultCountValidation',
						sourceSql : $("textarea#sourceCustomQuery").val(),
						targetSql : $("textarea#destinationCustomQuery").val(),
					};
					var resultID = "#customQueryResultCountValidationResult";
					sendRequest(data, resultID);
				} else {
					alert("Check custom validation menu");
					$('#resultCountValidation').prop('checked', false);
				}
			} else {
				alert("Establish connection first");
				$('#resultCountValidation').prop('checked', false);
			}
		} else {
			$("#customQueryResultCountValidationResult").html("");
			$('#resultCountValidation').prop('checked', false);
		}
	});
});

$(function() {
	$("#reportgenerator").click(function() {
		if (connectionCount == 0) {
			$.ajax({
				method : 'post',
				url : 'ValidationServlet',
				data : {
					validationType : 'generateReport'
				},
				datatype : 'json',
				success : function(responseText) {
					alert(responseText);
				}
			});
		} else {
			alert("Establish connection first and perform validations.");
		}
	});
});
