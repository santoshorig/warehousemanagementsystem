$(function () {

    'use strict';

    $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    var current = location.pathname;
    $('.nav-item-link').each(function () {
        if ($(this).attr('href').indexOf(current) !== -1) {
            $(this).parents(".nav-item").addClass('active');
        }
    });

});