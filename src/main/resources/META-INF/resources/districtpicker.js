$(function () {
    $('select.district-select').each(function () {
        var $this = $(this);

        $.getJSON($this.data('json-url'),function (data) {
            $.each(data,function (key,val) {
                $this.append(new Option(val.name,val.id));
            });
            $this.selectpicker('refresh');
        });

    });
});