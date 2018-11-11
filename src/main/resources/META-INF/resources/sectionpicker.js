(function( $ ) {
    $.fn.sectionpicker = function(options) {

        var settings = $.extend({
            'json-url' : '',
            'fetch-size' : 20
        },options);

        var offset = -1;


        return this.each(function() {
            var $this = $(this);
            if (offset < 0){
                offset = 0
            }else{
                offset = offset + settings['fetch-size'];
            }
            var url =  settings['json-url'] + '?count=' + settings['fetch-size'] + '&offset=' + offset

            var key = $.trim($this.find('.select-menu-text-filter input[type="text"]').val());

            if (key && (key != "")){
                url = url + '&key=' + key;
            }
            $.getJSON(url,function (data) {
                $.each(data.data,function (key,val) {

                    $this.find('.select-menu-list > .last-visible').before('<a class="select-menu-item last-visible">val.id</a>')
                });
                if (!data.next){
                    $this.find('.select-menu-list > .last-visible').remove();
                }
            });
        });

    };


    $(window).on('load.sectionpicker', function () {
        $('[data-remote-picker="section"]').each(function () {
            var $this = $(this);
            $this.sectionpicker({'json-url' : $this.data('json-url'),'fetch-size' : $this.data('fetch-size')});
        })
    })
})( jQuery );